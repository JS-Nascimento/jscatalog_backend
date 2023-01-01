package js.dev.jstec.jscatalog_backend.rest.DTOS;

import js.dev.jstec.jscatalog_backend.domain.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Integer id;
    private String name;
    private LocalDate created_Date;
    private LocalDate updated_Date;
    public CategoryDTO( Category entity ){
        this.id = entity.getId();
        this.name = entity.getName();
        this.created_Date = entity.getCreated_Date();
        this.updated_Date = entity.getUpdated_Date();
    }

}
