package komodocrypto.model;

import org.springframework.http.HttpStatus;

public class RootResponse {

    private HttpStatus status;
    private String message;
    private Object data;

    public RootResponse(HttpStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

}
