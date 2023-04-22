package js.dev.jstec.jscatalog_backend.rest.resources.exception.validation;

import js.dev.jstec.jscatalog_backend.domain.entities.User;
import js.dev.jstec.jscatalog_backend.domain.repositories.UserRepository;
import js.dev.jstec.jscatalog_backend.rest.DTOS.UserUpdateDTO;
import js.dev.jstec.jscatalog_backend.rest.resources.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    @Autowired
    private UserRepository repository;
    @Autowired
    private HttpServletRequest request;

    @Override
    public void initialize(UserUpdateValid ann) {
    }

    @Override
    public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {

        Map<String, String> requestVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long userId = Long.parseLong(requestVars.get("id"));

        List<FieldMessage> list = new ArrayList<>();
        User user = repository.findByEmail(dto.getEmail());
        if (user != null && userId != user.getId()) {
            list.add(new FieldMessage("email", "Já existe um usuário cadastrado com esse email."));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
