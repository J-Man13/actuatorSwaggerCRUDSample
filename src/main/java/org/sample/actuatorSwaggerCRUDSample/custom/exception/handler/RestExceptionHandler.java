package org.sample.actuatorSwaggerCRUDSample.custom.exception.handler;

import org.sample.actuatorSwaggerCRUDSample.custom.exception.GlobalUnhandledException;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.MongoDocumentNotFoundException;
import org.sample.actuatorSwaggerCRUDSample.model.CommonMessageDTO;
import org.sample.actuatorSwaggerCRUDSample.model.CommonUnsuccessfulResponseDTO;
import org.sample.actuatorSwaggerCRUDSample.model.ErrorDesriptor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MongoDocumentNotFoundException.class)
    public ResponseEntity mongoDocumentNotFoundExceptionHandler(MongoDocumentNotFoundException mongoDocumentNotFoundException) {
        ErrorDesriptor errorDesriptor = mongoDocumentNotFoundException.getErrorDesriptor();
        return new ResponseEntity(new CommonUnsuccessfulResponseDTO(HttpStatus.NOT_FOUND.value(), new CommonMessageDTO("error", errorDesriptor.getDescription()), errorDesriptor), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GlobalUnhandledException.class)
    public ResponseEntity globalUnhandledException(GlobalUnhandledException globalUnhandledException) {
        ErrorDesriptor errorDesriptor = globalUnhandledException.getErrorDesriptor();
        return new ResponseEntity(new CommonUnsuccessfulResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), new CommonMessageDTO("error", errorDesriptor.getDescription()), errorDesriptor), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception exception) {
        ErrorDesriptor errorDesriptor = new ErrorDesriptor(exception.getStackTrace()[0].getClassName(),
                exception.getMessage(),exception.getClass().getCanonicalName());
        return new ResponseEntity(new CommonUnsuccessfulResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), new CommonMessageDTO("error", errorDesriptor.getDescription()), errorDesriptor), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    protected ResponseEntity handleNoHandlerFoundException(NoHandlerFoundException noHandlerFoundException, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDesriptor errorDesriptor = new ErrorDesriptor(noHandlerFoundException.getStackTrace()[0].getClassName(),
                String.format("%s route does not exist", noHandlerFoundException.getRequestURL()),
                noHandlerFoundException.getClass().getCanonicalName());
        return new ResponseEntity(new CommonUnsuccessfulResponseDTO(HttpStatus.NOT_FOUND.value(), new CommonMessageDTO("error", errorDesriptor.getDescription()), errorDesriptor), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDesriptor errorDesriptor = new ErrorDesriptor(httpRequestMethodNotSupportedException.getStackTrace()[0].getClassName(),
                String.format("%s method is not supported for %s route", httpRequestMethodNotSupportedException.getMethod(),((ServletWebRequest)request).getRequest().getRequestURI()),
                httpRequestMethodNotSupportedException.getClass().getCanonicalName());
        return new ResponseEntity(new CommonUnsuccessfulResponseDTO(HttpStatus.METHOD_NOT_ALLOWED.value(), new CommonMessageDTO("error", errorDesriptor.getDescription()), errorDesriptor), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException httpMessageNotReadableException, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String route = ((ServletWebRequest)request).getRequest().getRequestURI();
        ErrorDesriptor errorDesriptor = new ErrorDesriptor(httpMessageNotReadableException.getStackTrace()[0].getClassName(),
                String.format("request body is not readable for %s route",route),
                httpMessageNotReadableException.getClass().getCanonicalName());
        return new ResponseEntity(new CommonUnsuccessfulResponseDTO(HttpStatus.BAD_REQUEST.value(), new CommonMessageDTO("error", errorDesriptor.getDescription()), errorDesriptor), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String route = ((ServletWebRequest)request).getRequest().getRequestURI();
        String method = ((ServletWebRequest)request).getRequest().getMethod();
        ErrorDesriptor errorDesriptor = new ErrorDesriptor(ex.getStackTrace()[0].getClassName(),
                String.format("request body did not pass common validation at %s route with %s method",route,method),
                ex.getClass().getCanonicalName());

        CommonUnsuccessfulResponseDTO commonUnsuccessfulResponseDTO = new CommonUnsuccessfulResponseDTO(HttpStatus.BAD_REQUEST.value(), new CommonMessageDTO("error", errorDesriptor.getDescription()), errorDesriptor);
        for (FieldError fieldError:ex.getBindingResult().getFieldErrors())
            commonUnsuccessfulResponseDTO.getMessages().add(new CommonMessageDTO("error",String.format("%s field did not pass validation, reason : %s",fieldError.getField(),fieldError.getDefaultMessage())));

        return new ResponseEntity(commonUnsuccessfulResponseDTO,HttpStatus.BAD_REQUEST);
    }
}
