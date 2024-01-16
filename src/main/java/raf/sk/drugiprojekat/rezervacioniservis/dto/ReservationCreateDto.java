package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class ReservationCreateDto {
    @NotNull @Positive
    private Long scheduledTrainingId;
    @NotNull @Positive
    private Long clientId;
}
