package js.dev.jstec.jscatalog_backend.services;
import static org.assertj.core.api.Assertions.*;
import js.dev.jstec.jscatalog_backend.Factory;
import js.dev.jstec.jscatalog_backend.domain.entities.Category;
import js.dev.jstec.jscatalog_backend.domain.entities.Product;
import js.dev.jstec.jscatalog_backend.domain.repositories.CategoryRepository;
import js.dev.jstec.jscatalog_backend.domain.repositories.ProductRepository;
import js.dev.jstec.jscatalog_backend.rest.DTOS.ProductDTO;
import js.dev.jstec.jscatalog_backend.service.exception.DatabaseIntegrityException;
import js.dev.jstec.jscatalog_backend.service.exception.ResourceNotFoundException;
import js.dev.jstec.jscatalog_backend.service.implemantation.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private Integer existingId;
    private Integer nonExistingId;
    private Integer dependentId;
    private PageImpl page;
    private Product product;
    private Category category;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp(){
        existingId = 1;
        nonExistingId = 1000;
        dependentId = 4;
        product = Factory.createProduct();
        category = Factory.createCategory();
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(product));


    }

    @Test
    public void shouldDoNothingWhenInvokeDeleteWithExistsId(){
        Mockito.doNothing().when(repository).deleteById(existingId);

        Assertions.assertDoesNotThrow(()->
                service.delete(existingId));

        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);

    }
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenInvokeDeleteWithNonExistsId(){
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);

        Assertions.assertThrows(ResourceNotFoundException.class, ()->
                service.delete(nonExistingId));

        Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);

    }
    @Test
    public void shouldThrowDatabaseIntegrityExceptionWhenInvokeDeleteWithDependentId(){
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        Assertions.assertThrows(DatabaseIntegrityException.class, ()->
                service.delete(dependentId));


        Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);

    }

    @Test
    public void shouldReturnAllObjectsPaged(){

        Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Pageable pageable = PageRequest.of(0,10);
        Page<ProductDTO> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);

    }

    @Test
    public void shouldUpdateProductWhenObjectIsValidAndIdExists() {
        Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
        Mockito.when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);


        ProductDTO result = service.update(existingId, productDTO);

        Assertions.assertNotNull(result);

    }
    @Test
    public void updateShouldThrowsExceptionWhenIdDoesNotExists() {
        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        Mockito.when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        assertThatThrownBy( () ->
                service.update(nonExistingId, productDTO)
        ).isInstanceOf(ResourceNotFoundException.class);

    }

    @Test
    public void shouldReturnValidProductWhenPassedValidId(){

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));

        ProductDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        Mockito.verify(repository, Mockito.times(1)).findById(existingId);


    }

    @Test
    public void shouldThrowExceptionWhenNonExistsId(){

        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy( () ->
                service.findById(nonExistingId)
        ).isInstanceOf(ResourceNotFoundException.class);
        Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);
    }

}
