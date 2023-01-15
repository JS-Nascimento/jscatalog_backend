package js.dev.jstec.jscatalog_backend;

import js.dev.jstec.jscatalog_backend.domain.entities.Category;
import js.dev.jstec.jscatalog_backend.domain.entities.Product;
import js.dev.jstec.jscatalog_backend.rest.DTOS.ProductDTO;

import java.time.Instant;
import java.time.LocalDate;

public class Factory {
    public static Product createProduct(){
        Product product = new Product(1,"Como comer menos","Livros explica", 14.99, "https://img.com;img.png",
                Instant.now());
        product.getCategories().add( createCategory() );
        return product;
    }
    public static Category createCategory(){

        return new Category(1,"Livros", LocalDate.now(), LocalDate.now()) ;
    }
    public static Product createNewProduct(){
        Product product = new Product(null,"Como comer menos","Livros explica", 14.99, "https://img.com;img.png",
                Instant.now());
        product.getCategories().add( new Category(1,"Livros", LocalDate.now(), LocalDate.now()) );
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
}
