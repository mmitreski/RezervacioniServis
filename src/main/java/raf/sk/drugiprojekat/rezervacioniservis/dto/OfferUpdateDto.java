package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferUpdateDto {
    private Long gymId;
    private Long trainingId;
    private Long price;
}
