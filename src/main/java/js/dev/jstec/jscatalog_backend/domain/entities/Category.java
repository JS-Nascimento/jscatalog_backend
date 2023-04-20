package js.dev.jstec.jscatalog_backend.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60)
    @NotEmpty
    @NotNull
    @NotBlank
    @Setter
    private String name;
    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @Setter
    private LocalDate created_Date;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @Setter
    private LocalDate updated_Date;

    @PrePersist
    public void prePersist() {
        created_Date = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        updated_Date = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
