package komodocrypto.exceptions.custom_exceptions;

import org.springframework.http.HttpStatus;

public class InsufficientFundsException extends Exception {

    private String message;
    private HttpStatus status;

    public InsufficientFundsException(String message, HttpStatus status) {
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
