package js.dev.jstec.jscatalog_backend.services;

import js.dev.jstec.jscatalog_backend.domain.repositories.ProductRepository;
import js.dev.jstec.jscatalog_backend.service.ProductService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;



}
