package raf.sk.drugiprojekat.rezervacioniservis.exception;

import org.springframework.http.HttpStatus;

public class ClientNotConfirmedException extends CustomException {

    public ClientNotConfirmedException(String message) {
        super(message, ErrorCode.CLIENT_NOT_REGISTERED, HttpStatus.FORBIDDEN);
    }
}
