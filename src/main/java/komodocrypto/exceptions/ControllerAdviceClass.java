package komodocrypto.exceptions;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ControllerAdviceClass {


    /**
     * Generates a nicely formatted Custom Exception response
     * for JSON output.
     *
     * @param e Any exception to be formatted
     * @return CustomException Object
     */
    public CustomException generateCustomEx(Exception e) {
        CustomException c = new CustomException();
        c.setErrorName(e.toString());
        c.setReason(e.getMessage());
        return c;
    }

    @ExceptionHandler(TableEmptyException.class)
    public @ResponseBody
    TableEmptyException tableEmpty(TableEmptyException e) {
        TableEmptyException error = new TableEmptyException();
        error.setMessage("No data found.");
        error.setStatus(204);
        return error;
    }
}