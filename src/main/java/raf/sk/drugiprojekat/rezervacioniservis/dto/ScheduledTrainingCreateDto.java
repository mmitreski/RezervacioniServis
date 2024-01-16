package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
