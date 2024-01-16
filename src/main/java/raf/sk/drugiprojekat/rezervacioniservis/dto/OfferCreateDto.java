package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class OfferCreateDto {
    @NotNull @Positive
    private Long gymId;
    @NotNull @Positive
    private Long trainingId;
    @NotNull @Positive
    private Long price;
}
