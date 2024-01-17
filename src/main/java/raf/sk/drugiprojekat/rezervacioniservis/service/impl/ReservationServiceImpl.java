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
import raf.sk.drugiprojekat.rezervacioniservis.mapper.ReservationMapper;
import raf.sk.drugiprojekat.rezervacioniservis.repository.ReservationRepository;
import raf.sk.drugiprojekat.rezervacioniservis.repository.ScheduledTrainingRepository;
import raf.sk.drugiprojekat.rezervacioniservis.service.ReservationService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {
    private ReservationMapper reservationMapper;
    private ReservationRepository reservationRepository;
    private ScheduledTrainingRepository scheduledTrainingRepository;
    private JmsTemplate jmsTemplate;
    private Retry reservationServiceRetry;
    @Value("${destination.createReserveUser}")
    private String reserveUser;
    @Value("${destination.createClientCancelUser")
    private String cancelUser;
    @Value("${destination.sendEmails}")
    private String mailNotification;

    public ReservationServiceImpl(ReservationMapper reservationMapper, ReservationRepository reservationRepository, JmsTemplate jmsTemplate, Retry reservationServiceRetry, ScheduledTrainingRepository scheduledTrainingRepository) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.jmsTemplate = jmsTemplate;
        this.reservationServiceRetry = reservationServiceRetry;
        this.scheduledTrainingRepository = scheduledTrainingRepository;
    }

    @Override
    public Page<ReservationDto> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable).map(reservationMapper::reservationToReservationDto);
    }

    @Override
    public ReservationDto findById(Long id) {
        return reservationRepository.findById(id).map(reservationMapper::reservationToReservationDto).orElseThrow(() -> new NotFoundException(String.format("RESERVATION [id: %d] NOT FOUND.", id)));
    }

    @Override
    public List<ReservationDto> findAllByScheduledTrainingId(Long scheduledTrainingId) {
        return reservationRepository.findAllReservationsByScheduledTraining_id(scheduledTrainingId).stream().map(reservationMapper::reservationToReservationDto).toList();
    }

    @Override
    public List<ReservationDto> findAllByClientId(Long clientId) {
        return reservationRepository.findAllReservationsByClientId(clientId).stream().map(reservationMapper::reservationToReservationDto).toList();
    }

    @Override
    public ReservationDto add(ReservationCreateDto reservationCreateDto) {
        ScheduledTraining scheduledTraining = scheduledTrainingRepository.findScheduledTrainingById(reservationCreateDto.getScheduledTrainingId()).orElseThrow(() -> new NotFoundException(String.format("SCHEDULED TRAINING [id: %d] NOT FOUND.", reservationCreateDto.getScheduledTrainingId())));
        ClientDto clientDto = null;
        try {
            WebClient client = WebClient.create("http://localhost:8080/api");
            WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = (WebClient.UriSpec<WebClient.RequestBodySpec>) client.get();
            clientDto = Retry.decorateSupplier(reservationServiceRetry, () -> uriSpec.uri("/client?id="+reservationCreateDto.getClientId()).retrieve().bodyToMono(ClientDto.class).block()).get();
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException(String.format("CLIENT [id: %d] NOT FOUND.", reservationCreateDto.getClientId()));
        } catch (Exception e) {
            new RuntimeException("Internal server error");
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
        String subject = "Zakazan trening";
        String content = "Postovani, klijent sa id " + reservationCreateDto.getClientId() + " rezervisao je trening " + scheduledTraining.getOffer().getTraining().getName() + " u terminu " + scheduledTraining.getTimeSlot() + ", fiskulturna sala " + scheduledTraining.getOffer().getGym().getName() + ".";
        jmsTemplate.convertAndSend(reserveUser, reservationCreateDto.getClientId());
        jmsTemplate.convertAndSend(mailNotification, new EmailCreateDto(clientDto.getEmail(), LocalDateTime.now(), subject, content));
        jmsTemplate.convertAndSend(mailNotification, new EmailCreateDto(managerDto.getEmail(), LocalDateTime.now(), subject, content));// to notification service
        return reservationMapper.reservationToReservationDto(reservationRepository.save(reservationMapper.reservationCreateDtoToReservation(clientDto, reservationCreateDto)));
    }

    @Override
    public void deleteById(Long id) {
        Reservation reservation = reservationRepository.findReservationById(id).orElseThrow(() -> new NotFoundException(String.format("RESERVATION [id: %d] NOT FOUND", id)));
        ScheduledTraining scheduledTraining = reservation.getScheduledTraining();
        ClientDto clientDto = null;
        try {
            WebClient client = WebClient.create("http://localhost:8080/api");
            WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = (WebClient.UriSpec<WebClient.RequestBodySpec>) client.get();
            clientDto = Retry.decorateSupplier(reservationServiceRetry, () -> uriSpec.uri("/client?id="+reservation.getClientId()).retrieve().bodyToMono(ClientDto.class).block()).get();
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException(String.format("CLIENT [id: %d] NOT FOUND.", reservation.getClientId()));
        } catch (Exception e) {
            new RuntimeException("Internal server error");
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
        String content = "Postovani, klijent sa id " + reservation.getClientId() + " otkazao je trening " + scheduledTraining.getOffer().getTraining().getName() + " u terminu " + scheduledTraining.getTimeSlot() + ", fiskulturna sala " + scheduledTraining.getOffer().getGym().getName() + ".";
        reservation.getScheduledTraining().setReservedSpots(reservation.getScheduledTraining().getReservedSpots()-1);
        jmsTemplate.convertAndSend(cancelUser, reservation.getClientId());
        jmsTemplate.convertAndSend(mailNotification, new EmailCreateDto(clientDto.getEmail(), LocalDateTime.now(), subject, content));
        jmsTemplate.convertAndSend(mailNotification, new EmailCreateDto(managerDto.getEmail(), LocalDateTime.now(), subject, content));// to notification service
        reservationRepository.deleteById(id);
    }
}
