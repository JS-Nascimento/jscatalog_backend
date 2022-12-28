package js.dev.jstec.jscatalog_backend.service.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message){
        super(message);
    }
}
