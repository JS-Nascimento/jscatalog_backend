package js.dev.jstec.jscatalog_backend.rest.resources;

import js.dev.jstec.jscatalog_backend.rest.DTOS.CategoryDTO;
import js.dev.jstec.jscatalog_backend.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

        @PostMapping
        public ResponseEntity<CategoryDTO> create ( @RequestBody CategoryDTO dto){
            dto = service.create(dto);
            URI uri = ServletUriComponentsBuilder
                            .fromCurrentRequest().path( "/{id}" )
                            .buildAndExpand( dto.getId() ).toUri();
            return ResponseEntity.created(uri).body( dto );
        }

}
