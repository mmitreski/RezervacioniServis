package raf.sk.drugiprojekat.rezervacioniservis.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Reservation;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ReservationCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ReservationDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ScheduledTrainingDto;
import raf.sk.drugiprojekat.rezervacioniservis.exception.NotFoundException;
import raf.sk.drugiprojekat.rezervacioniservis.mapper.ReservationMapper;
import raf.sk.drugiprojekat.rezervacioniservis.repository.ReservationRepository;
import raf.sk.drugiprojekat.rezervacioniservis.service.ReservationService;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {
    private ReservationMapper reservationMapper;
    private ReservationRepository reservationRepository;
    private JmsTemplate jmsTemplate;
    @Value("${destination.createReserveUser}")
    private String reserveUser;
    @Value("${destination.createClientCancelUser")
    private String cancelUser;
    @Value("${destination.createReserveNotification}")
    private String reserveNotification;
    @Value("${destination.createClientCancelNotification}")
    private String cancelNotification;

    public ReservationServiceImpl(ReservationMapper reservationMapper, ReservationRepository reservationRepository, JmsTemplate jmsTemplate) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.jmsTemplate = jmsTemplate;
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
        jmsTemplate.convertAndSend(reserveUser, reservationCreateDto.getClientId());
        jmsTemplate.convertAndSend(reserveNotification, new Object()); // to notification service
        return reservationMapper.reservationToReservationDto(reservationRepository.save(reservationMapper.reservationCreateDtoToReservation(reservationCreateDto)));
    }

    @Override
    public void deleteById(Long id) {
        Reservation reservation = reservationRepository.findReservationById(id).orElseThrow(() -> new NotFoundException(String.format("RESERVATION [id: %d] NOT FOUND", id)));
        reservation.getScheduledTraining().setReservedSpots(reservation.getScheduledTraining().getReservedSpots()-1);
        jmsTemplate.convertAndSend(cancelUser, reservation.getClientId());
        jmsTemplate.convertAndSend(cancelNotification,  new Object()); // to notification service
        reservationRepository.deleteById(id);
    }
}
