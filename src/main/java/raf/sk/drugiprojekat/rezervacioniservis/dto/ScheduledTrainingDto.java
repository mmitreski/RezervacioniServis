package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduledTrainingDto {
    private Long id;
    private OfferDto offerDto;
    private LocalDateTime timeSlot;
    private Long reservedSpots;
    private Long max;
}
