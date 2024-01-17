package raf.sk.drugiprojekat.rezervacioniservis.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ClientDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String username;
    private Long cardId;
    private Long trainingsReserved;
    private Boolean forbidden;
    private Boolean confirmed;
}
