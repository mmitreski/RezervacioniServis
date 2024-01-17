package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
public class ReservationCreateDto {
    @NotNull @Positive
    private Long scheduledTrainingId;
    @NotNull @Positive
    private Long clientId;
}
