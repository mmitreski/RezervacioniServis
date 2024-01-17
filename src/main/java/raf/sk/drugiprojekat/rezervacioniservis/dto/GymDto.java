package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GymDto {
    private Long id;
    private String name;
    private String description;
    private Long trainerNumber;
    private Long managerId;
    private Long trainingDiscountNumber;
}
