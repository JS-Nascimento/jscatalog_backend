package js.dev.jstec.jscatalog_backend.service;


import js.dev.jstec.jscatalog_backend.rest.DTOS.UserDTO;
import js.dev.jstec.jscatalog_backend.rest.DTOS.UserInsertDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    UserDTO findById(Long id);

    UserDTO create(UserInsertDTO dto);

    UserDTO update(Long id, UserDTO dto);

    void delete(Long id);

    Page<UserDTO> findAllPaged(Pageable pageable);
}
