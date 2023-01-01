package js.dev.jstec.jscatalog_backend.service;


import js.dev.jstec.jscatalog_backend.rest.DTOS.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface ProductService {
    ProductDTO findById( Integer id);

    ProductDTO create ( ProductDTO dto );

    ProductDTO update (Integer id,  ProductDTO dto );

    void delete ( Integer id );

    Page<ProductDTO> findAllPaged ( PageRequest pageRequest );
}
