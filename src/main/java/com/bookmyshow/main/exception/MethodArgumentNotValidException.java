package com.bookmyshow.main.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class MethodArgumentNotValidException extends RuntimeException {

    private String message;         // Error message
    private List<FieldError> fieldErrors;  // List of validation errors (FieldError objects)

    // Constructor to initialize the error message and field errors
    public MethodArgumentNotValidException(String message, List<FieldError> fieldErrors) {
        super(message); 
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    // Custom method to format the errors into a user-friendly string
    public String getFormattedErrors() {
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        for (FieldError error : fieldErrors) {
            errorMessage.append(error.getField())  // The field name with error
                        .append(" - ")
                        .append(error.getDefaultMessage())  // The error message
                        .append("; ");
        }
        return errorMessage.toString();
    }
}
