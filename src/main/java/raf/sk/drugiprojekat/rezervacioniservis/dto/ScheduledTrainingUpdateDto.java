package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduledTrainingUpdateDto {
    private Long offerId;
    private LocalDateTime timeSlot;
}
