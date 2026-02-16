package net.pvytykac;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<ProblemDetail> onNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage()))
                .build();
    }
}
