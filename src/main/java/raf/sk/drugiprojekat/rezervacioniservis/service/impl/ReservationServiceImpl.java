package raf.sk.drugiprojekat.rezervacioniservis.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ReservationCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ReservationDto;
import raf.sk.drugiprojekat.rezervacioniservis.exception.NotFoundException;
import raf.sk.drugiprojekat.rezervacioniservis.mapper.ReservationMapper;
import raf.sk.drugiprojekat.rezervacioniservis.repository.ReservationRepository;
import raf.sk.drugiprojekat.rezervacioniservis.service.ReservationService;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private ReservationMapper reservationMapper;
    private ReservationRepository reservationRepository;
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
        return reservationMapper.reservationToReservationDto(reservationRepository.save(reservationMapper.reservationCreateDtoToReservation(reservationCreateDto)));
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
