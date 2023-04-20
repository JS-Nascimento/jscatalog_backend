package js.dev.jstec.jscatalog_backend.rest.DTOS;

import lombok.Data;

@Data
public class UserInsertDTO extends UserDTO {

    private String password;

}
