package js.dev.jstec.jscatalog_backend.service.implemantation;

import js.dev.jstec.jscatalog_backend.domain.entities.Role;
import js.dev.jstec.jscatalog_backend.domain.entities.User;
import js.dev.jstec.jscatalog_backend.domain.repositories.RoleRepository;
import js.dev.jstec.jscatalog_backend.domain.repositories.UserRepository;
import js.dev.jstec.jscatalog_backend.rest.DTOS.RoleDTO;
import js.dev.jstec.jscatalog_backend.rest.DTOS.UserDTO;
import js.dev.jstec.jscatalog_backend.rest.DTOS.UserInsertDTO;
import js.dev.jstec.jscatalog_backend.service.UserService;
import js.dev.jstec.jscatalog_backend.service.exception.DatabaseIntegrityException;
import js.dev.jstec.jscatalog_backend.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;

    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> list = repository.findAll(pageable);
        return list.map(User -> (new UserDTO(User)));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return repository.findById(id)
                .map(User -> (new UserDTO(User)))
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não existe."));
    }


    @Override
    @Transactional
    public UserDTO create(UserInsertDTO dto) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(mapperToEntity(dto, user));
        return new UserDTO(user);
    }


    @Override
    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        try {
            User user = repository.getReferenceById(id);
            repository.save(mapperToEntity(dto, user));
            return new UserDTO(user);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto não encontrado.");
        }

    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Produto não encontrado.");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Violação de integridade relacional.");
        }

    }


    private User mapperToEntity(UserDTO dto, User entity) {

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());


        entity.getRoles().clear();
        for (RoleDTO roleDto : dto.getRoles()) {
            Role role = roleRepository.getReferenceById(roleDto.getId());
            entity.getRoles().add(role);
        }
        return entity;
    }
}
