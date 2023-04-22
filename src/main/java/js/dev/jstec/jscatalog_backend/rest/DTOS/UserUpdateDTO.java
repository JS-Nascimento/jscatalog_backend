package js.dev.jstec.jscatalog_backend.rest.DTOS;

import js.dev.jstec.jscatalog_backend.rest.resources.exception.validation.UserUpdateValid;
import lombok.Data;

@Data
@UserUpdateValid
public class UserUpdateDTO extends UserDTO {
    public UserUpdateDTO() {
        super();
    }
}
