package js.dev.jstec.jscatalog_backend.rest.resources;

import js.dev.jstec.jscatalog_backend.rest.DTOS.CategoryDTO;
import js.dev.jstec.jscatalog_backend.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value ="/api/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity
                .ok()
                .body(service.findAll());
    }
    @GetMapping("{id}")
    public CategoryDTO findById ( @PathVariable Integer id ) {
        return service.findById( id );


    }

}
