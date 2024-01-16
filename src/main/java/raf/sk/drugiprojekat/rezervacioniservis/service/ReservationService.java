package raf.sk.drugiprojekat.rezervacioniservis.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Reservation;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ReservationCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ReservationDto;

import java.util.List;

public interface ReservationService {
    Page<ReservationDto> findAll(Pageable pageable);
    ReservationDto findById(Long id);
    List<ReservationDto> findAllByScheduledTrainingId(Long scheduledTrainingId);
    List<ReservationDto> findAllByClientId(Long clientId);
    ReservationDto add(ReservationCreateDto reservationCreateDto);
    void deleteById(Long id);
}
