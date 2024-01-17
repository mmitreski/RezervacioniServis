package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class TrainingCreateDto {
    @NotBlank
    private String name;
    @NotNull
    private Boolean individual;
}
