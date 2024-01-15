package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
public class OfferCreateDto {
    @Positive
    private Long gymId;
    @Positive
    private Long trainingId;
    @Positive
    private Long price;
}
