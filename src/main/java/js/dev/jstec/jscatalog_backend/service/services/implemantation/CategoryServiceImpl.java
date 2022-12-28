package js.dev.jstec.jscatalog_backend.service.services.implemantation;

import js.dev.jstec.jscatalog_backend.domain.entities.Category;
import js.dev.jstec.jscatalog_backend.domain.repositories.CategoryRepository;
import js.dev.jstec.jscatalog_backend.service.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository repository;
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }


        public List<Category> findAll(){

        return new ArrayList<>(repository.findAll().stream().toList());

        }

}
