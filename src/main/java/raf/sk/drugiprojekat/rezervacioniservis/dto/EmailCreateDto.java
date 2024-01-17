package raf.sk.drugiprojekat.rezervacioniservis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EmailCreateDto {
    @Email
    private String addressTo;
    @NotNull
    private LocalDateTime timeSent;
    @NotBlank
    private String subject;
    @NotBlank
    private String content;
}
