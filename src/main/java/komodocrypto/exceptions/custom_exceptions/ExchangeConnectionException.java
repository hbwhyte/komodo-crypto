package komodocrypto.exceptions.custom_exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when unable to connect to the Exchange
 */
public class ExchangeConnectionException extends Exception {

    private String message;
    private HttpStatus status;

    public ExchangeConnectionException (String message, HttpStatus status) {
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
