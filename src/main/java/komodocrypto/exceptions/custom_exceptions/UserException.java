package komodocrypto.exceptions.custom_exceptions;

import org.springframework.http.HttpStatus;

public class UserException extends Exception {

    private String message;
    private HttpStatus status;

    public UserException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
