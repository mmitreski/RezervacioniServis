package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GymUpdateDto {
    private String name;
    private String description;
    private Long trainerNumber;
    private Long managerId;
    private Long trainingDiscountNumber;
}
