package js.dev.jstec.jscatalog_backend.rest.resources.exception;

import js.dev.jstec.jscatalog_backend.service.exception.DatabaseIntegrityException;
import js.dev.jstec.jscatalog_backend.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound( ResourceNotFoundException e, HttpServletRequest request ){
        StandardError err = StandardError.builder()
                            .timeStamp( Instant.now() )
                            .status( HttpStatus.NOT_FOUND.value() )
                            .error( "Resource not found." )
                            .message( e.getMessage() )
                            .path( request.getRequestURI() )
                            .build()
                            ;

        return ResponseEntity.status( HttpStatus.NOT_FOUND )
                .body( err );

    }
    @ExceptionHandler(DatabaseIntegrityException.class)
    public ResponseEntity<StandardError> databaseIntegrity(DatabaseIntegrityException e, HttpServletRequest request ){
        StandardError err = StandardError.builder()
                .timeStamp( Instant.now() )
                .status( HttpStatus.BAD_REQUEST.value() )
                .error("Integrity database violation.")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(err);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError err = new ValidationError();
        err.setTimeStamp(Instant.now());
        err.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        err.setError("Validation Exception.");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }


        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(err);

    }

}
