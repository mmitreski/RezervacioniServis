package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
public class ReservationCreateDto {
    @Positive
    private Long scheduledTrainingId;
    @Positive
    private Long clientId;
}
