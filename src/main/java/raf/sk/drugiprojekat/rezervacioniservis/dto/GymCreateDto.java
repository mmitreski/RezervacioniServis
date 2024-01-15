package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class GymCreateDto {
    @NotBlank
    private String name;
    private String description;
    private Long trainerNumber;
    @Positive
    private Long managerId;
}
