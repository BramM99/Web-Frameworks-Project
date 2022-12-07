package app.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class ScooterPreConditionFailed extends RuntimeException {
    public ScooterPreConditionFailed(String message) {
        super(message);
    }
}
