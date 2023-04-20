package js.dev.jstec.jscatalog_backend.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Entity
@Table
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Column(length = 60)
    @NotEmpty
    @NotNull
    @NotBlank
    @Getter
    @Setter
    private String name;

    @Column(columnDefinition = "TEXT")
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private Double price;
    @Getter
    @Setter
    private String imageUrl;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @Getter
    @Setter
    private Instant date;
    @Getter
    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnore
    Set <Category> categories = new HashSet <>();

    public Product ( Integer id, String name, String description, Double price, String imageUrl, Instant date ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.date = date;

    }
}
