package js.dev.jstec.jscatalog_backend.domain;
import static org.assertj.core.api.Assertions.*;

import js.dev.jstec.jscatalog_backend.Factory;

import js.dev.jstec.jscatalog_backend.domain.entities.Product;
import js.dev.jstec.jscatalog_backend.domain.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;


import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    private Integer existingId;
    private Integer nonExistingId;

    private  Product newProduct ;

    private Integer quantityProducts;
    @BeforeEach
    void setUp() {
        existingId =1;
        nonExistingId = 1000;
        quantityProducts =25;
        newProduct = Factory.createNewProduct();
    }

    @Test
    public void shouldDeleteObjectWhenIdExists(){

        productRepository.deleteById( existingId );
        Optional <Product> founded = productRepository.findById( existingId ) ;
        assertThat( founded ).isNotPresent();

    }
    @Test
    public void shouldThrowExceptionWhenDoesNotIdExists(){

        assertThatThrownBy(  () ->
            productRepository.deleteById( nonExistingId )
        ).isInstanceOf( EmptyResultDataAccessException.class  );

    }
    @Test
    public void shouldPersistWithAutoIncrementWhenIdIsNull (){

        Product product = productRepository.save( newProduct );
        assertThatObject( product ).isNotNull();
        assertThat(product.getId()).isGreaterThan( quantityProducts );
        assertThat(product.getName()).isEqualTo( newProduct.getName() );
        assertThat(product.getDescription()).isEqualTo( newProduct.getDescription() );
        assertThat(product.getImageUrl()).isEqualTo( newProduct.getImageUrl() );
        assertThat(product.getPrice()).isEqualTo( newProduct.getPrice() );
        assertThat(product.getCategories() ).isSameAs( newProduct.getCategories());


    }

    @Test
    public void shouldReturnObjectWhenExists(){

        Product product = Factory.createProduct();
        Optional <Product> founded = productRepository.findById( product.getId() ) ;
        assertThat( founded ).isPresent();
        assertThat( founded.get().getId() ).isEqualTo( product.getId() );

    }
    @Test
    public void shouldThrowExceptionWhenObjectDoesNotExists(){

        Optional <Product> founded = productRepository.findById( nonExistingId ) ;
        assertThat( founded ).isNotPresent();
        assertThat( founded ).isEmpty()
                .isNotPresent()
                ;

    }



}
