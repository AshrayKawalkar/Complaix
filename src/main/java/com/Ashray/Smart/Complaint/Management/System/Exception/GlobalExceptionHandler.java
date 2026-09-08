package com.Ashray.Smart.Complaint.Management.System.Exception;

import com.Ashray.Smart.Complaint.Management.System.Department.Exception.DepartmentNotFoundException;
import com.Ashray.Smart.Complaint.Management.System.User.Exception.EmailAlredyExitsException;
import com.Ashray.Smart.Complaint.Management.System.User.Exception.InvalidCredentialsException;
import com.Ashray.Smart.Complaint.Management.System.User.Exception.PhoneNumberAlreadyExitsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlredyExitsException.class)
    public ResponseEntity<ErrorResponce> emailAlreadyExits(EmailAlredyExitsException ex) {

        ErrorResponce error = new ErrorResponce(

                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );

        return  new ResponseEntity<>(error,HttpStatus.CONFLICT);

    }

    @ExceptionHandler(PhoneNumberAlreadyExitsException.class)
    public ResponseEntity<ErrorResponce> phoneNumberAlreadyExits(PhoneNumberAlreadyExitsException ex) {

        ErrorResponce error= new ErrorResponce(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage()

        );

        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponce> methodArgumentNotValid(MethodArgumentNotValidException ex) {


        StringBuilder message = new StringBuilder();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {

            message.append(fieldError.getDefaultMessage())
                    .append(", ");

        }

            ErrorResponce error = new ErrorResponce(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                message.toString()
        );

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponce> invalidCredentialException(InvalidCredentialsException ex) {

        ErrorResponce error = new ErrorResponce(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponce> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
        ErrorResponce error = new ErrorResponce(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
