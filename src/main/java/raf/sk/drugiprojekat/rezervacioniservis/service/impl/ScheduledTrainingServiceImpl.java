package raf.sk.drugiprojekat.rezervacioniservis.service.impl;

import io.github.resilience4j.retry.Retry;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Reservation;
import raf.sk.drugiprojekat.rezervacioniservis.domain.ScheduledTraining;
import raf.sk.drugiprojekat.rezervacioniservis.dto.*;
import raf.sk.drugiprojekat.rezervacioniservis.exception.NotFoundException;
import raf.sk.drugiprojekat.rezervacioniservis.mapper.ScheduledTrainingMapper;
import raf.sk.drugiprojekat.rezervacioniservis.repository.ScheduledTrainingRepository;
import raf.sk.drugiprojekat.rezervacioniservis.service.ScheduledTrainingService;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ScheduledTrainingServiceImpl implements ScheduledTrainingService {
    private ScheduledTrainingRepository scheduledTrainingRepository;
    private ScheduledTrainingMapper scheduledTrainingMapper;
    private JmsTemplate jmsTemplate;
    private Retry reservationServiceRetry;
    @Value("${destination.sendEmails}")
    private String mailNotification;
    @Value("${destination.createClientCancelUser")
    private String cancelUser;
    private Comparator<ScheduledTrainingDto> comparator = (o1, o2) -> {
        if(o1.getTimeSlot().isBefore(o2.getTimeSlot()))
            return -1;
        else if(o1.getTimeSlot().isEqual(o2.getTimeSlot()))
            return 0;
        else
            return 1;
    };

    public ScheduledTrainingServiceImpl(ScheduledTrainingRepository scheduledTrainingRepository, ScheduledTrainingMapper scheduledTrainingMapper, JmsTemplate jmsTemplate, Retry reservationServiceRetry) {
        this.scheduledTrainingMapper = scheduledTrainingMapper;
        this.scheduledTrainingRepository = scheduledTrainingRepository;
        this.jmsTemplate = jmsTemplate;
        this.reservationServiceRetry = reservationServiceRetry;
    }

    @Override
    public Page<ScheduledTrainingDto> findAll(Pageable pageable) {
        return scheduledTrainingRepository.findAll(pageable).map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto);
    }

    @Override
    public ScheduledTrainingDto findById(Long id) {
        return scheduledTrainingRepository.findById(id).map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).orElseThrow(() -> new NotFoundException(String.format("SCHEDULED TRAINING [id: %d] NOT FOUND.", id)));
    }

    @Override
    public ScheduledTrainingDto findByOfferIdTimeSlot(Long offerId, LocalDateTime timeSlot) {
        return scheduledTrainingRepository.findScheduledTrainingByOffer_idAndTimeSlot(offerId, timeSlot).map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).orElseThrow(() -> new NotFoundException(String.format("SCHEDULED TRAINING [offerId: %d, timeSlot: %s] NOT FOUND.", offerId, timeSlot.toString())));
    }

    @Override
    public List<ScheduledTrainingDto> findByGymNameAndTrainingNameAndTrainingIndividualAndReservedSpotsLessThan(String gymName, String trainingName, Boolean trainingIndividual, Long reservedSpots) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_gym_nameAndOffer_training_nameAndOffer_training_individualAndReservedSpotsLessThan(gymName, trainingName, trainingIndividual, reservedSpots).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByTimeSlot(LocalDateTime timeSlot) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByTimeSlot(timeSlot).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByTimeSlotBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByTimeSlotBetween(startDate, endDate).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByGymName(String gymName) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_gym_name(gymName).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByTrainingNameAndTrainingIndividual(String trainingName, Boolean trainingIndividual) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_training_nameAndOffer_training_individual(trainingName, trainingIndividual).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByGymNameAndTrainingNameAndTrainingIndividual(String gymName, String trainingName, Boolean trainingIndividual) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_gym_nameAndOffer_training_nameAndOffer_training_individual(gymName, trainingName, trainingIndividual).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }


    @Override
    public List<ScheduledTrainingDto> findByTrainingIndividual(Boolean trainingIndividual) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_training_individual(trainingIndividual).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByGymNameAndTrainingIndividual(String gymName, Boolean trainingIndividual) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_gym_nameAndOffer_training_individual(gymName, trainingIndividual).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }
    @Override
    public List<ScheduledTrainingDto> findByGymNameAndWeekday(String gymName, String dayOfWeek) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_gym_name(gymName).stream().filter(scheduledTraining ->
            scheduledTraining.getTimeSlot().getDayOfWeek().equals(DayOfWeek.valueOf(dayOfWeek))).map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByGymNameAndTimeSlotBetween(String gymName, LocalDateTime startDate, LocalDateTime endDate) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_gym_nameAndTimeSlotBetween(gymName, startDate, endDate).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByTimeSlotFree(LocalDateTime timeSlot) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByTimeSlotWhereReservedSpotsLessThanMax(timeSlot).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByTimeSlotBetweenFree(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByTimeSlotBetweenWhereReservedSpotsLessThanMax(startDate, endDate).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByGymNameFree(String gymName) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_gym_nameWhereReservedSpotsLessThanMax(gymName).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByTrainingNameAndTrainingIndividualFree(String trainingName, Boolean trainingIndividual) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_training_nameAndOffer_training_individualWhereReservedSpotsLessThanMax(trainingName, trainingIndividual).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByGymNameAndTrainingNameAndTrainingIndividualFree(String gymName, String trainingName, Boolean trainingIndividual) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_gym_nameAndOffer_training_nameAndOffer_training_individualWhereReservedSpotsLessThanMax(gymName, trainingName, trainingIndividual).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }


    @Override
    public List<ScheduledTrainingDto> findByTrainingIndividualFree(Boolean trainingIndividual) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_training_individualWhereReservedSpotsLessThanMax(trainingIndividual).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByGymNameAndTrainingIndividualFree(String gymName, Boolean trainingIndividual) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_gym_nameAndOffer_training_individualWhereReservedSpotsLessThanMax(gymName, trainingIndividual).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }
    @Override
    public List<ScheduledTrainingDto> findByGymNameAndWeekdayFree(String gymName, String dayOfWeek) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_gym_nameWhereReservedSpotsLessThanMax(gymName).stream().filter(scheduledTraining -> scheduledTraining.getTimeSlot().getDayOfWeek().equals(DayOfWeek.valueOf(dayOfWeek))).map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }

    @Override
    public List<ScheduledTrainingDto> findByGymNameAndTimeSlotBetweenFree(String gymName, LocalDateTime startDate, LocalDateTime endDate) {
        return scheduledTrainingRepository.findAllScheduledTrainingsByOffer_gym_nameAndTimeSlotBetweenWhereReservedSpotsLessThanMax(gymName, startDate, endDate).stream().map(scheduledTrainingMapper::scheduledTrainingToScheduledTrainingDto).sorted(comparator).toList();
    }

    @Override
    public ScheduledTrainingDto create(ScheduledTrainingCreateDto scheduledTrainingCreateDto) {
        return scheduledTrainingMapper.scheduledTrainingToScheduledTrainingDto(scheduledTrainingRepository.save(scheduledTrainingMapper.scheduledTrainingCreateDtoToScheduledTraining(scheduledTrainingCreateDto)));
    }

    @Override
    public ScheduledTrainingDto update(Long id, ScheduledTrainingUpdateDto scheduledTrainingUpdateDto) {
        return scheduledTrainingMapper.scheduledTrainingToScheduledTrainingDto(scheduledTrainingRepository.save(scheduledTrainingMapper.scheduledTrainingUpdateDtoToScheduledTraining(id,scheduledTrainingUpdateDto)));
    }

    @Override
    public void deleteById(Long id) {
        ScheduledTraining scheduledTraining = scheduledTrainingRepository.findById(id).orElseThrow(()-> new NotFoundException(String.format("SCHEDULED TRAINING [id: %d] NOT FOUND", id)));
        Map<Long, String> clientMails = new HashMap<>();
        for(Reservation r : scheduledTraining.getReservations()) {
            ClientDto clientDto = null;
            try {
                WebClient client = WebClient.create("http://localhost:8080/api");
                WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = (WebClient.UriSpec<WebClient.RequestBodySpec>) client.get();
                clientDto = Retry.decorateSupplier(reservationServiceRetry, () -> uriSpec.uri("/client?id="+r.getClientId()).retrieve().bodyToMono(ClientDto.class).block()).get();
            } catch (HttpClientErrorException e) {
                if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                    throw new NotFoundException(String.format("CLIENT [id: %d] NOT FOUND.", r.getClientId()));
            } catch (Exception e) {
                new RuntimeException("Internal server error");
            }
            clientMails.put(r.getClientId(), clientDto.getEmail());
        }
        ManagerDto managerDto = null;
        try {
            WebClient client = WebClient.create("http://localhost:8080/api");
            WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = (WebClient.UriSpec<WebClient.RequestBodySpec>) client.get();
            managerDto = Retry.decorateSupplier(reservationServiceRetry, () -> uriSpec.uri("/manager?id="+scheduledTraining.getOffer().getGym().getManagerId()).retrieve().bodyToMono(ManagerDto.class).block()).get();
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException(String.format("MANAGER [id: %d] NOT FOUND.", scheduledTraining.getOffer().getGym().getManagerId()));
        } catch (Exception e) {
            new RuntimeException("Internal server error");
        }
        String subject = "Otkazan trening";
        String content = "Postovani, obavestavamo vas da je trening " + scheduledTraining.getOffer().getTraining().getName() + " zakazan za " + scheduledTraining.getTimeSlot() + " u fiskulturnoj sali " + scheduledTraining.getOffer().getGym().getName() + " otkazan.";
        for(Reservation r : scheduledTraining.getReservations()) {
            jmsTemplate.convertAndSend(cancelUser, r.getClientId());
            jmsTemplate.convertAndSend(mailNotification, new EmailCreateDto(clientMails.get(r.getClientId()), LocalDateTime.now(), subject, content));
        }
        jmsTemplate.convertAndSend(mailNotification, new EmailCreateDto(managerDto.getEmail(), LocalDateTime.now(), subject, content)); // to notification service
        scheduledTrainingRepository.deleteById(id);
    }
}
