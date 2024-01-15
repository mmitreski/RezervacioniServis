package raf.sk.drugiprojekat.rezervacioniservis.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Reservation;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ReservationCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ReservationDto;
import raf.sk.drugiprojekat.rezervacioniservis.exception.NotFoundException;
import raf.sk.drugiprojekat.rezervacioniservis.repository.ScheduledTrainingRepository;

@Component
@AllArgsConstructor
public class ReservationMapper {
    private ScheduledTrainingMapper scheduledTrainingMapper;
    private ScheduledTrainingRepository scheduledTrainingRepository;
    public ReservationDto reservationToReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setScheduledTrainingDto(scheduledTrainingMapper.scheduledTrainingToScheduledTrainingDto(reservation.getScheduledTraining()));
        reservationDto.setDiscountedPrice(reservation.getDiscountedPrice());
        reservationDto.setClientId(reservationDto.getClientId());
        return reservationDto;
    }

    public Reservation reservationCreateDtoToReservation(ReservationCreateDto reservationCreateDto) {
        Reservation reservation = new Reservation();
        reservation.setClientId(reservationCreateDto.getClientId());
        // send the request for the discounted price?
        reservation.setScheduledTraining(scheduledTrainingRepository.findScheduledTrainingById(reservationCreateDto.getScheduledTrainingId()).orElseThrow(() -> new NotFoundException(String
                .format("SCHEDULED TRAINING [id: %d] NOT FOUND.", reservationCreateDto.getScheduledTrainingId()))));
        return reservation;
    }
}
