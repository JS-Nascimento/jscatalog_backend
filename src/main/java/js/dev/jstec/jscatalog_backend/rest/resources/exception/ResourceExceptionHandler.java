package js.dev.jstec.jscatalog_backend.rest.resources.exception;

import js.dev.jstec.jscatalog_backend.service.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound( EntityNotFoundException e, HttpServletRequest request ){
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

}
