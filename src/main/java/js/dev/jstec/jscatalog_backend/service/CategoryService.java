package js.dev.jstec.jscatalog_backend.service;


import js.dev.jstec.jscatalog_backend.domain.entities.Category;
import js.dev.jstec.jscatalog_backend.rest.DTOS.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CategoryService {
    public CategoryDTO findById( Integer id);

    CategoryDTO create ( CategoryDTO dto );

    CategoryDTO update (Integer id,  CategoryDTO dto );

    void delete ( Integer id );

    Page<CategoryDTO> findAllPaged ( Pageable pageable );
}
