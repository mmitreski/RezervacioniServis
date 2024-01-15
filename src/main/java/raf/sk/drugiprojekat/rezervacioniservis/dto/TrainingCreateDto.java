package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TrainingCreateDto {
    @NotBlank
    private String name;
    @NotNull
    private Boolean individual;
}
