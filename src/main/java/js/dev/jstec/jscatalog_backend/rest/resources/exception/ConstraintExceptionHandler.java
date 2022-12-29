package js.dev.jstec.jscatalog_backend.rest.resources.exception;

import js.dev.jstec.jscatalog_backend.service.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class ConstraintExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> constraintViolation( ConstraintViolationException e, HttpServletRequest request ){

        StandardError err = StandardError.builder()
                            .timeStamp( Instant.now() )
                            .status( HttpStatus.BAD_REQUEST.value() )
                            .error( "Constraint Violation." )
                            .message(e.getMessage().toString() )
                            .path( request.getRequestURI() )
                            .build()
                            ;

        return ResponseEntity.status( HttpStatus.BAD_REQUEST)
                .body( err );

    }

}
