package js.dev.jstec.jscatalog_backend.rest.resources;

import js.dev.jstec.jscatalog_backend.rest.DTOS.CategoryDTO;
import js.dev.jstec.jscatalog_backend.rest.DTOS.ProductDTO;
import js.dev.jstec.jscatalog_backend.service.CategoryService;
import js.dev.jstec.jscatalog_backend.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value ="/api/products")
public class ProductController {
        private final ProductService service;

        public ProductController ( ProductService service) {
            this.service = service;
        }

        @GetMapping
        public ResponseEntity<Page <ProductDTO>> findAll( Pageable pageable ) {
            Page<ProductDTO> list = service.findAllPaged(pageable);
            return ResponseEntity
                    .ok()
                    .body(list);
        }
        @GetMapping("{id}")
        public ProductDTO findById ( @PathVariable Integer id ) {
            return service.findById( id );
        }

        @PostMapping
        public ResponseEntity<ProductDTO> create ( @RequestBody ProductDTO dto){
            dto = service.create(dto);
            URI uri = ServletUriComponentsBuilder
                            .fromCurrentRequest().path( "/{id}" )
                            .buildAndExpand( dto.getId() ).toUri();
            return ResponseEntity.created(uri).body( dto );
        }
        @PutMapping("{id}")
        public ResponseEntity<ProductDTO> update (@PathVariable Integer id,  @RequestBody ProductDTO dto){
            dto = service.update(id, dto);

            return ResponseEntity.ok().body( dto );
        }
        @DeleteMapping("{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void delete (@PathVariable Integer id){
            service.delete(id);
        }


}
