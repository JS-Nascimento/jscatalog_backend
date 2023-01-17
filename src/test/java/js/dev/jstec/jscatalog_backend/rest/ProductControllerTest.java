package js.dev.jstec.jscatalog_backend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import js.dev.jstec.jscatalog_backend.Factory;
import js.dev.jstec.jscatalog_backend.rest.DTOS.ProductDTO;
import js.dev.jstec.jscatalog_backend.rest.resources.ProductController;
import js.dev.jstec.jscatalog_backend.service.ProductService;
import js.dev.jstec.jscatalog_backend.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;


import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    private PageImpl<ProductDTO> page;
    private ProductDTO productDTO;
    private int existingId;
    private int nonExistingId;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1;
        nonExistingId = 1000;

        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(productDTO));
        Mockito.when(service.findAllPaged(ArgumentMatchers.any())).thenReturn(page);
        Mockito.when(service.findById(existingId)).thenReturn(productDTO);
        Mockito.when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(service.update(eq(existingId), ArgumentMatchers.any())).thenReturn(productDTO);
        Mockito.when(service.update(eq(nonExistingId),ArgumentMatchers.any())).thenThrow(ResourceNotFoundException.class);
    }
    @Test
    public void findAllPagedShouldReturnPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                ;

    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                ;

    }

    @Test
    public void findByIdShouldNotFoundWhenIdDoesNotExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                ;

    }


    @Test
    public void updateShouldReturnProductWhenIdExists() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
        ;

    }

    @Test
    public void updateShouldNotFoundWhenIdDoesNotExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}",nonExistingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isNotFound())
        ;

    }
}
