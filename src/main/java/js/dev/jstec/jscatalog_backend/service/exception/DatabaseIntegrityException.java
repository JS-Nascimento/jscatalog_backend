package js.dev.jstec.jscatalog_backend.service.exception;

public class DatabaseIntegrityException extends RuntimeException {
    public DatabaseIntegrityException ( String message){
        super(message);
    }
}
