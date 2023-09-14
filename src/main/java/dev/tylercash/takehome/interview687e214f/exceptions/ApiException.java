package dev.tylercash.takehome.interview687e214f.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ApiException extends RuntimeException {
    private HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(500);
    public ApiException(String message){
        super(message);
    }

    public ApiException(String message, HttpStatusCode httpStatusCode){
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
}
