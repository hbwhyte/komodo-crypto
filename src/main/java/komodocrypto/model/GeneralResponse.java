package komodocrypto.model;

import org.springframework.http.HttpStatus;

public class GeneralResponse {

    String status;
    HttpStatus response_code;
    Object data;

    public GeneralResponse(Object[] data) {
        status = "Success";
        response_code = HttpStatus.OK;
        this.data = data;
    }

    public GeneralResponse() {
        status = "Success";
        response_code = HttpStatus.OK;
    }

    public String getStatus() {return status; }

    public void setStatus(String status) {
        this.status = status;
    }

    public HttpStatus getResponse_code() {
        return response_code;
    }

    public void setResponse_code(HttpStatus response_code) {
        this.response_code = response_code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }
}
