package komodocrypto.exceptions;


import komodocrypto.exceptions.custom_exceptions.ClientException;
import komodocrypto.exceptions.custom_exceptions.IndicatorException;
import komodocrypto.exceptions.custom_exceptions.InsufficientFundsException;
import komodocrypto.exceptions.custom_exceptions.TableEmptyException;
import komodocrypto.exceptions.custom_exceptions.UserException;
import komodocrypto.model.RootResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ClientException.class)
    protected @ResponseBody
    RootResponse clientError(ClientException ex) {
        return new RootResponse(ex.getStatus(), ex.getMessage(), null);
    }

    @ExceptionHandler(value = IndicatorException.class)
    protected @ResponseBody
    RootResponse indicatorError(IndicatorException ex) {
        return new RootResponse(ex.getStatus(), ex.getMessage(), null);
    }

    @ExceptionHandler(value = TableEmptyException.class)
    protected @ResponseBody
    RootResponse tableEmptyError(TableEmptyException ex) {
        return new RootResponse(HttpStatus.valueOf(ex.getStatus()), ex.getMessage(), null);
    }

//    @ExceptionHandler(value=InsufficientFundsException.class)
//    protected @ResponseBody RootResponse insufficientFundsError(InsufficientFundsException ex) {
//        return new RootResponse(HttpStatus.valueOf(ex.getStatus()), ex.getMessage(), null);

    @ExceptionHandler(value = UserException.class)
    protected @ResponseBody
    RootResponse userException(UserException ex) {
        return new RootResponse(HttpStatus.valueOf(ex.getStatus()), ex.getMessage(), null);
    }

//    @ExceptionHandler(TableEmptyException.class)
//    public @ResponseBody
//    TableEmptyException tableEmpty(TableEmptyException e) {
//        TableEmptyException error = new TableEmptyException();
//        error.setMessage("No data found.");
//        error.setStatus(204);
//        return error;
//    }

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
