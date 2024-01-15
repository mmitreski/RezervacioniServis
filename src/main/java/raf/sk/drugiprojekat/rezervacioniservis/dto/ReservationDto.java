package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDto {
    private Long id;
    private ScheduledTrainingDto scheduledTrainingDto;
    private Long discountedPrice;
    private Long clientId;
}
