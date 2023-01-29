package js.dev.jstec.jscatalog_backend.service;

import js.dev.jstec.jscatalog_backend.domain.repositories.ProductRepository;
import js.dev.jstec.jscatalog_backend.rest.DTOS.ProductDTO;
import js.dev.jstec.jscatalog_backend.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class ProductServiceIT {
    @Autowired
    private ProductService service;
    @Autowired
    private ProductRepository repository;

    private Integer existingID;
    private Integer nonExistingID;
    private Integer countTotalProducts;

    @BeforeEach
    public void setUP(){
        existingID = 1;
        nonExistingID = 1000;
        countTotalProducts = 25;
    }
    @Test
    public void deleteShouldDeleteProductWhenIdExists(){
        service.delete( existingID );

        Assertions.assertEquals( countTotalProducts -1 , repository.count() );
    }

    @Test
    public void deleteShouldThrowExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows( ResourceNotFoundException.class ,() ->
                                service.delete( nonExistingID )
                                                                 );
    }
    @Test
    public void findAllPagedShouldReturnPageWhenThereAreObjects(){

        PageRequest pageRequest = PageRequest.of( 0,10 );

        Page <ProductDTO> result = service.findAllPaged( pageRequest );

        Assertions.assertFalse( result.isEmpty() );
        Assertions.assertEquals( 0, result.getNumber() );
        Assertions.assertEquals( 10, result.getSize() );
        Assertions.assertEquals( countTotalProducts,(int) result.getTotalElements());

    }
    @Test
    public void findAllPagedShouldReturnEmptyPageWhenActualPageDoNotExist(){

        PageRequest pageRequest = PageRequest.of( 50,10 );

        Page <ProductDTO> result = service.findAllPaged( pageRequest );

        Assertions.assertFalse( result.isEmpty() );
    }
    @Test
    public void findAllPagedShouldReturnSortedPageWhenRequestSortedPage(){

        PageRequest pageRequest = PageRequest.of( 0,10, Sort.by( "name" ) );
        Page <ProductDTO> result = service.findAllPaged( pageRequest );
        Assertions.assertFalse( result.isEmpty() );
        Assertions.assertEquals( "Macbook Pro", result.getContent().get( 0 ).getName() );
        Assertions.assertEquals( "PC Gamer", result.getContent().get( 1 ).getName() );
        Assertions.assertEquals( "PC Gamer Alfa", result.getContent().get( 2 ).getName() );
        Assertions.assertEquals( pageRequest.getSort().isSorted(), result.getSort().isSorted() );
    }
}
