package raf.sk.drugiprojekat.rezervacioniservis.exception;

import org.springframework.http.HttpStatus;

public class ClientForbiddenException extends CustomException {
    public ClientForbiddenException(String message) {
        super(message, ErrorCode.CLIENT_FORBIDDEN, HttpStatus.FORBIDDEN);
    }
}
