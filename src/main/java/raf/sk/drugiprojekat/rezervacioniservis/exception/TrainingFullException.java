package raf.sk.drugiprojekat.rezervacioniservis.exception;

import org.springframework.http.HttpStatus;

public class TrainingFullException extends CustomException{
    public TrainingFullException(String message) {
        super(message, ErrorCode.TRAINING_FULL, HttpStatus.NOT_ACCEPTABLE);
    }
}
