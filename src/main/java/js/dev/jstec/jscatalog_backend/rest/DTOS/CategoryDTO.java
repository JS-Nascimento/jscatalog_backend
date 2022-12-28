package js.dev.jstec.jscatalog_backend.rest.DTOS;

import js.dev.jstec.jscatalog_backend.domain.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Integer Id;
    private String name;
    private LocalDate created_Date;

    public CategoryDTO( @NotNull Category entity ){
        this.Id = entity.getId();
        this.name = entity.getName();
        this.created_Date = entity.getCreated_Date();
    }

}
