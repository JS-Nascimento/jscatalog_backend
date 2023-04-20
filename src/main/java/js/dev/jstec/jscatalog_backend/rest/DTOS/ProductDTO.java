package js.dev.jstec.jscatalog_backend.rest.DTOS;

import js.dev.jstec.jscatalog_backend.domain.entities.Category;
import js.dev.jstec.jscatalog_backend.domain.entities.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductDTO {

    private Integer id;
    @Size(min = 5, max = 60, message = "Muita letra ou pouca letra faz direito")
    @NotBlank(message = "Nome precisa ser válido")
    private String name;
    @NotBlank(message = "Descrição precisa ser válido")
    private String description;

    @Positive(message = "Preço deve ser maior que zero.")
    private Double price;
    private String imageUrl;
    @PastOrPresent(message = "data não pode ser futura")
    private Instant date;

    private final List<CategoryDTO> categoriesDto = new ArrayList<>();

    public ProductDTO(Integer id, String name, String description, Double price, String imageUrl, Instant date) {
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
        categories.forEach(category -> this.categoriesDto.add(new CategoryDTO(category)));
    }
}
