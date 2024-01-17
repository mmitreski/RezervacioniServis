package raf.sk.drugiprojekat.rezervacioniservis.mapper;

import io.github.resilience4j.retry.Retry;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import raf.sk.drugiprojekat.rezervacioniservis.domain.Reservation;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ClientDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ReservationCreateDto;
import raf.sk.drugiprojekat.rezervacioniservis.dto.ReservationDto;
import raf.sk.drugiprojekat.rezervacioniservis.exception.ClientForbiddenException;
import raf.sk.drugiprojekat.rezervacioniservis.exception.ClientNotConfirmedException;
import raf.sk.drugiprojekat.rezervacioniservis.exception.NotFoundException;
import raf.sk.drugiprojekat.rezervacioniservis.exception.TrainingFullException;
import raf.sk.drugiprojekat.rezervacioniservis.repository.ScheduledTrainingRepository;

@Component
@AllArgsConstructor
public class ReservationMapper {
    private ScheduledTrainingMapper scheduledTrainingMapper;
    private ScheduledTrainingRepository scheduledTrainingRepository;
    private Retry reservationServiceRetry;
    public ReservationDto reservationToReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setScheduledTrainingDto(scheduledTrainingMapper.scheduledTrainingToScheduledTrainingDto(reservation.getScheduledTraining()));
        reservationDto.setDiscountedPrice(reservation.getDiscountedPrice());
        reservationDto.setClientId(reservationDto.getClientId());
        return reservationDto;
    }

    public Reservation reservationCreateDtoToReservation(ClientDto clientDto, ReservationCreateDto reservationCreateDto) {
        Reservation reservation = new Reservation();
        reservation.setClientId(reservationCreateDto.getClientId());
        reservation.setScheduledTraining(scheduledTrainingRepository.findScheduledTrainingById(reservationCreateDto.getScheduledTrainingId()).orElseThrow(() -> new NotFoundException(String.format("SCHEDULED TRAINING [id: %d] NOT FOUND.", reservationCreateDto.getScheduledTrainingId()))));
        if(reservation.getScheduledTraining().getOffer().getGym().getTrainingDiscountNumber() != 0 && clientDto.getTrainingsReserved() % reservation.getScheduledTraining().getOffer().getGym().getTrainingDiscountNumber() == 0) {
            reservation.setDiscountedPrice(0L);
        } else {
            reservation.setDiscountedPrice(reservation.getScheduledTraining().getOffer().getPrice());
        }

        if(!clientDto.getConfirmed())
            throw new ClientNotConfirmedException(String.format("CLIENT [id: %d] NOT CONFIRMED", clientDto.getId()));
        if(clientDto.getForbidden())
            throw new ClientForbiddenException(String.format("CLIENT [id: %d] FORBIDDEN", clientDto.getId()));
        if(reservation.getScheduledTraining().getReservedSpots() == reservation.getScheduledTraining().getMax())
            throw new TrainingFullException(String.format("TRAINING [id: %d] IS FULL", reservation.getId()));

        reservation.getScheduledTraining().setReservedSpots(reservation.getScheduledTraining().getReservedSpots()+1);
        return reservation;
    }
}
