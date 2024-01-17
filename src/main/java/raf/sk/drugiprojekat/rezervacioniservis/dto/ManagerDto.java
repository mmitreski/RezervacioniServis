package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ManagerDto {
    private Long id;
    private String email;
    private String username;
    private LocalDate birthDate;
    private String name;
    private String surname;
    private String gymName;
    private LocalDate employmentDate;
}
