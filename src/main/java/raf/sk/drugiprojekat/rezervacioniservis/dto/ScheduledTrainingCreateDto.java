package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduledTrainingCreateDto {
    @NotNull @Positive
    private Long offerId;
    @NotNull
    private LocalDateTime timeSlot;
    @Positive
    private Long max;
}
