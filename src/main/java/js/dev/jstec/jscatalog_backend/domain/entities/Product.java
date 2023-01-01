package js.dev.jstec.jscatalog_backend.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60)
    @NotEmpty
    @NotNull
    @NotBlank
    private String name;
    private String description;
    private Double price;
    private String imageUrl;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant date;

    Set <Category> categories = new HashSet <>();

    public Product ( Integer id, String name, String description, Double price, String imageUrl, Instant date ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.date = date;

    }

    public void setId ( Integer id ) {
        this.id = id;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public void setDescription ( String description ) {
        this.description = description;
    }

    public void setPrice ( Double price ) {
        this.price = price;
    }

    public void setImageUrl ( String imageUrl ) {
        this.imageUrl = imageUrl;
    }

    public void setDate ( Instant date ) {
        this.date = date;
    }

    @Override
    public boolean equals ( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals( product.id );
    }

    @Override
    public int hashCode () {
        return Objects.hash( id );
    }
}
