package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
public class GymCreateDto {
    @NotBlank
    private String name;
    private String description;
    private Long trainerNumber;
    private Long trainingDiscountNumber;
    @NotNull @Positive
    private Long managerId;
}
