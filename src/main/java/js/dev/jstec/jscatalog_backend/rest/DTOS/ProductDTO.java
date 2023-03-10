package js.dev.jstec.jscatalog_backend.rest.DTOS;

import js.dev.jstec.jscatalog_backend.domain.entities.Category;
import js.dev.jstec.jscatalog_backend.domain.entities.Product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductDTO {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Instant date;

    private List <CategoryDTO> categories = new ArrayList <>();

    public ProductDTO ( Integer id, String name, String description, Double price, String imageUrl, Instant date ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    public ProductDTO ( Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imageUrl = entity.getImageUrl();
        this.date = entity.getDate();
    }
    public ProductDTO ( Product entity, Set <Category> categories) {
        this(entity);
        categories.forEach( category -> this.categories.add( new CategoryDTO(category) ) );
    }
}
