package ru.savelyev.analyzer.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.savelyev.analyzer.exceptions.StringLengthViolenceException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(StringLengthViolenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleNotFoundException(StringLengthViolenceException e) {
        log.error(e.getMessage(), e);
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}