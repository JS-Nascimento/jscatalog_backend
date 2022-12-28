package js.dev.jstec.jscatalog_backend.service.services;


import js.dev.jstec.jscatalog_backend.domain.entities.Category;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    public List<Category> findAll();
}
