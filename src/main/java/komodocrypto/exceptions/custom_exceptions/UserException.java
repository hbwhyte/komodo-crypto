package komodocrypto.exceptions.custom_exceptions;

public class UserException extends Exception {

    int status;

    public UserException(String message) {
        super(message);
        this.status = 400;
    }

    @Override
    public String toString() {
        return "UserException{} " + super.getMessage();
    }

    public int getStatus() {
        return status;
    }
}
