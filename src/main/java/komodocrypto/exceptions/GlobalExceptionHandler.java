package komodocrypto.exceptions;


import komodocrypto.exceptions.custom_exceptions.ClientException;
import komodocrypto.model.RootResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value=ClientException.class)
    protected @ResponseBody RootResponse clientError(ClientException ex) {
        return new RootResponse(ex.getStatus(), ex.getMessage(), null);
    }

    /**
     * Generates a nicely formatted Custom Exception response
     * for JSON output.
     *
     * @param e Any exception to be formatted
     * @return CustomException Object
     */
//    public CustomException generateCustomEx(Exception e) {
//        CustomException c = new CustomException();
//        c.setErrorName(e.toString());
//        c.setReason(e.getMessage());
//        return c;
//    }
}