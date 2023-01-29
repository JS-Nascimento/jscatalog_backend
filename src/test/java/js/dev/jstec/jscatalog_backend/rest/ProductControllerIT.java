package js.dev.jstec.jscatalog_backend.rest;

import js.dev.jstec.jscatalog_backend.Factory;
import js.dev.jstec.jscatalog_backend.domain.entities.Category;
import js.dev.jstec.jscatalog_backend.domain.entities.Product;
import js.dev.jstec.jscatalog_backend.rest.DTOS.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private Integer existingId;
    private Integer nonExistingId;
    private Integer dependentId;
    private PageImpl page;
    private Product product;
    private Category category;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp(){
        existingId = 1;
        nonExistingId = 1000;
        dependentId = 4;
        product = Factory.createProduct();
        category = Factory.createCategory();
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>( List.of(product));

    }

    @Test
    public void findAllShouldReturnSortedPageWhenGetSortedPage() throws Exception{

        ResultActions result =
                mockMvc.perform( MockMvcRequestBuilders.get("/api/products?page=0&size=12&sort=name,asc")
                        .accept( MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect( jsonPath("$.pageable.pageSize").value( 12 ) )
                        .andExpect(jsonPath("$.pageable.sort.sorted" ).value( true ))
                        .andExpect( jsonPath( "$.totalElements" ).value( 25 ) )

                ;
    }
}
