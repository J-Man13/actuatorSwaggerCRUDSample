package org.sample.actuatorSwaggerCRUDSample.exception.handler;

import org.sample.actuatorSwaggerCRUDSample.exception.GlobalUnhandledException;
import org.sample.actuatorSwaggerCRUDSample.exception.MongoDocumentNotFoundException;
import org.sample.actuatorSwaggerCRUDSample.model.CommonMessageDTO;
import org.sample.actuatorSwaggerCRUDSample.model.CommonUnsuccessfulResponseDTO;
import org.sample.actuatorSwaggerCRUDSample.model.ErrorDesriptor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
    public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MongoDocumentNotFoundException.class)
    public ResponseEntity mongoDocumentNotFoundExceptionHandler(MongoDocumentNotFoundException mongoDocumentNotFoundException) {
        ErrorDesriptor errorDesriptor = mongoDocumentNotFoundException.getErrorDesriptor();
        return new ResponseEntity(new CommonUnsuccessfulResponseDTO(HttpStatus.NOT_FOUND.value(),new CommonMessageDTO("error",errorDesriptor.getDescription()),errorDesriptor),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GlobalUnhandledException.class)
    public ResponseEntity globalUnhandledException(GlobalUnhandledException globalUnhandledException){
        ErrorDesriptor errorDesriptor = globalUnhandledException.getErrorDesriptor();
        return new ResponseEntity(new CommonUnsuccessfulResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),new CommonMessageDTO("error",errorDesriptor.getDescription()),errorDesriptor),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception exception){
        ErrorDesriptor errorDesriptor = new ErrorDesriptor(exception.getStackTrace()[0].getClass().getCanonicalName(),exception.getMessage());
        return new ResponseEntity(new CommonUnsuccessfulResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),new CommonMessageDTO("error",errorDesriptor.getDescription()),errorDesriptor),HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    protected ResponseEntity handleNoHandlerFoundException(NoHandlerFoundException noHandlerFoundException, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDesriptor errorDesriptor = new ErrorDesriptor(noHandlerFoundException.getStackTrace()[0].getClass().getCanonicalName(),String.format("%s route does not exist",noHandlerFoundException.getRequestURL()));
        return new ResponseEntity(new CommonUnsuccessfulResponseDTO(HttpStatus.NOT_FOUND.value(),new CommonMessageDTO("error",errorDesriptor.getDescription()),errorDesriptor),HttpStatus.NOT_FOUND);
    }
}
