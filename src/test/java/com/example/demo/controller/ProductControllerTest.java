package com.example.demo.controller;

import com.example.demo.model.MyResponse;
import com.example.demo.model.PagenatedData;
import com.example.demo.model.Product;
import com.example.demo.model.ProductReview;
import com.example.demo.service.ProductReviewService;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ProductController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductReviewService productReviewService;

    @Autowired
    private ObjectMapper objectMapper;

    Product product;

    @BeforeEach
    public void setup(){

        product = Product.builder()
                .id(1)
                .name("Jock's System")
                .description("Unit test")
                .imageURL("test")
                .price(10.1)
                .createDate(Instant.now())
                .updateDate(Instant.now())
                .isRelease(true)
                .build();

    }
    @Test
    @Order(1)
    void addProduct() throws Exception{
        // precondition
        given(productService.addProduct(any(Product.class))).willReturn(product);

        // action
        ResultActions response = mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(MyResponse.ok(product))));

        // verify
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is(product.getName())))
                .andExpect(jsonPath("$.data.description", is(product.getDescription())))
                .andExpect(jsonPath("$.data.imageURL", is(product.getImageURL())));
    }

    @Test
    @Order(2)
    void getProduct() throws Exception{
        // precondition
        given(productService.getProductById(product.getId())).willReturn(product);

        // action
        ResultActions response = mockMvc.perform(get("/product/{productId}", product.getId()));

        // verify
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.name", is(product.getName())))
                .andExpect(jsonPath("$.data.description", is(product.getDescription())))
                .andExpect(jsonPath("$.data.imageURL", is(product.getImageURL())));
    }

    @Test
    @Order(3)
    void getAllProducts() throws Exception{
        PagenatedData<Product> result = new PagenatedData<>(List.of(product),1,10,List.of(product).size(),1);
        given(productService.getAllProducts(any(), any())).willReturn(result);
        // action
        ResultActions response = mockMvc.perform(get("/product/products"));

        // verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.totalCount",
                        is(List.of(product).size())));
    }




    @Test
    @Order(4)
    void updateProduct() throws Exception{
        // precondition
        given(productService.getProductById(product.getId())).willReturn(product);
        product.setName("MyTest");
        product.setDescription("jock@gmail.com");
        given(productService.updateProduct(product)).willReturn(product);

        // action
        ResultActions response = mockMvc.perform(put("/product/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        // verify
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.name", is(product.getName())))
                .andExpect(jsonPath("$.data.description", is(product.getDescription())));
    }

    @Test
    @Order(5)
    void deleteProduct() throws Exception{
        // precondition
        willDoNothing().given(productService).deleteProduct(product.getId());

        // action
        ResultActions response = mockMvc.perform(delete("/product/{id}", product.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }


}