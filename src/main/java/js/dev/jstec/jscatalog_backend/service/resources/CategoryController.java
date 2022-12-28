package js.dev.jstec.jscatalog_backend.service.resources;

import js.dev.jstec.jscatalog_backend.domain.entities.Category;
import js.dev.jstec.jscatalog_backend.service.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value ="/api/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
//        List<Category> list = new ArrayList<>();
//        list.add(new Category(1, "Books"));
//        list.add(new Category(2, "Magazines"));
        return ResponseEntity
                .ok()
                .body(service.findAll());
    }

}
