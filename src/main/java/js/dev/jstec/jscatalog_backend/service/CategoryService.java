package js.dev.jstec.jscatalog_backend.service;


import js.dev.jstec.jscatalog_backend.domain.entities.Category;
import js.dev.jstec.jscatalog_backend.rest.DTOS.CategoryDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CategoryService {


    public List<CategoryDTO> findAll();

    public CategoryDTO findById( Integer Id);
}
