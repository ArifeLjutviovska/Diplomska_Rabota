package diplomskarabota.demo.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException() {
        super(CompanyNotFoundException.class.getSimpleName());
    }

    public CompanyNotFoundException(String message) {
        super(message);
    }
}
