package com.rohit.storemanager.exceptions;

import com.rohit.storemanager.models.ApiResponse;
import com.rohit.storemanager.models.ResponseStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.NonUniqueResultException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NullPointerException.class)
    public ApiResponse HandleNullPointer(NullPointerException e) {
        return ApiResponse
                .builder()
                .responseStatus(ResponseStatus.Failure)
                .message(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler(value = NonUniqueResultException.class)
    public ApiResponse handleNonUniqueResultException(NonUniqueResultException nonUniqueResultException) {
        return ApiResponse
                .builder()
                .responseStatus(ResponseStatus.Failure)
                .message(nonUniqueResultException.getLocalizedMessage())
                .build();
    }
}
