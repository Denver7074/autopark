package ru.denver7074.autopark.api.response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.denver7074.autopark.utils.Errors;

import java.util.Objects;

import static ru.denver7074.autopark.utils.Errors.E001;


@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public Object handleException(HandlerMethodValidationException t) {
        return ResponseApi.negativeResponse(E001.name(), E001.getDescription(),
                ExceptionUtils.getStackTrace(t));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Errors.CustomException.class)
    public Object handleException(Errors.CustomException t) {
        return ResponseApi.negativeResponse(t.getError().name(), t.getMessage(),
                ExceptionUtils.getStackTrace(t));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleException(MethodArgumentNotValidException t) {
        FieldError error = t.getBindingResult().getFieldError();
        return ResponseApi.negativeResponse(Errors.E002.name(),
                String.format(Errors.E002.getDescription(),
                        Objects.requireNonNull(error).getField()),
                ExceptionUtils.getStackTrace(t)
        );
    }

}
