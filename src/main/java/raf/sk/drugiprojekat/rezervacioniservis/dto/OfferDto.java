package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferDto {
    private Long id;
    private GymDto gymDto;
    private TrainingDto trainingDto;
    private Long price;
}
