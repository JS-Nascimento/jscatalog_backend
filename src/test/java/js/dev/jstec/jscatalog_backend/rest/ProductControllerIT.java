package js.dev.jstec.jscatalog_backend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import js.dev.jstec.jscatalog_backend.Factory;
import js.dev.jstec.jscatalog_backend.rest.DTOS.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

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



    private ProductDTO productDTO;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        existingId = 1;
        nonExistingId = 1000;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());



        productDTO = Factory.createProductDTO();


    }

    @Test
    public void findAllShouldReturnSortedPageWhenGetSortedPage() throws Exception{


                mockMvc.perform( MockMvcRequestBuilders.get("/api/products?page=0&size=12&sort=name,asc")
                        .accept( MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect( jsonPath("$.pageable.pageSize").value( 12 ) )
                        .andExpect(jsonPath("$.pageable.sort.sorted" ).value( true ))
                        .andExpect( jsonPath( "$.totalElements" ).value( 25 ) )

                ;
    }

    @Test
    public void updateShouldReturnProductWhenIdExists() throws Exception {
        String expectedName = productDTO.getName();

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value( existingId ))
                .andExpect(jsonPath("$.name").value( expectedName ))
        ;

    }
    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {


        String jsonBody = objectMapper.writeValueAsString(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}", nonExistingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())

        ;

    }
}
