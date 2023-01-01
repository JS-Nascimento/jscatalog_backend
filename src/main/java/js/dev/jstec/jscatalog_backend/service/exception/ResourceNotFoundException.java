package js.dev.jstec.jscatalog_backend.service.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException ( String message){
        super(message);
    }
}
