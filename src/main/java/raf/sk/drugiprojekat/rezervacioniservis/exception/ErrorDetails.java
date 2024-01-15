package raf.sk.drugiprojekat.rezervacioniservis.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ErrorDetails {
    @JsonProperty("error_code")
    private ErrorCode errorCode;
    @JsonProperty("error_message")
    private String errorMessage;
    private Instant timestamp;
}