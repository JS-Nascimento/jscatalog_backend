package js.dev.jstec.jscatalog_backend.service;


import js.dev.jstec.jscatalog_backend.rest.DTOS.UserDTO;
import js.dev.jstec.jscatalog_backend.rest.DTOS.UserInsertDTO;
import js.dev.jstec.jscatalog_backend.rest.DTOS.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    UserDTO findById(Long id);

    UserDTO create(UserInsertDTO dto);

    UserDTO update(Long id, UserUpdateDTO dto);

    void delete(Long id);

    Page<UserDTO> findAllPaged(Pageable pageable);
}
