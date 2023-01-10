package js.dev.jstec.jscatalog_backend.services;

import js.dev.jstec.jscatalog_backend.domain.repositories.ProductRepository;
import js.dev.jstec.jscatalog_backend.service.exception.DatabaseIntegrityException;
import js.dev.jstec.jscatalog_backend.service.exception.ResourceNotFoundException;
import js.dev.jstec.jscatalog_backend.service.implemantation.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductRepository repository;

    private Integer existingId;
    private Integer nonExistingId;
    private Integer dependentId;
    @BeforeEach
    void setUp(){
        existingId = 1;
        nonExistingId = 1000;
        dependentId = 4;

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
    }

    @Test
    public void shouldDoNothingWhenInvokeDeleteWithExistsId(){

        Assertions.assertDoesNotThrow(()->
                service.delete(existingId));

        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);


    }
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenInvokeDeleteWithNonExistsId(){

        Assertions.assertThrows(ResourceNotFoundException.class, ()->
                service.delete(nonExistingId));

        Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);


    }
    @Test
    public void shouldThrowDatabaseIntegrityExceptionWhenInvokeDeleteWithDependentId(){

        Assertions.assertThrows(DatabaseIntegrityException.class, ()->
                service.delete(dependentId));

        Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);


    }

}
