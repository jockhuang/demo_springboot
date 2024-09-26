package com.example.demo.repository;

import com.example.demo.model.Product;
import jakarta.annotation.Resource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductRepositoryTest {
    @Resource
    ProductRepository repository;

    @Test
    @DisplayName("Test 1:Save Product Test")
    @Order(1)
    @Rollback(value = false)
    public void saveProductTest(){

        //Action
        Product product = Product.builder()
                .name("Jock's System")
                .description("Unit test")
                .imageURL("test")
                .price(10.1)
                .createDate(Instant.now())
                .updateDate(Instant.now())
                .isRelease(true)
                .build();

        repository.save(product);

        //Verify
        System.out.println(product);
        Assertions.assertThat(product.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getProductTest(){

        //Action
        Product product = repository.findById(1).get();
        //Verify
        System.out.println(product);
        Assertions.assertThat(product.getId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void getListOfProductTest(){
        //Action
        Pageable paging = PageRequest.of(1, 10);
        Page<Product> products = repository.findByNameContaining("Jock", paging);
        //Verify
        System.out.println(products.getTotalElements());
        Assertions.assertThat(products.getTotalElements()).isGreaterThan(0);

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateProductTest(){

        //Action
        Product product = repository.findById(1).get();
        product.setDescription("jockhuang@gmail.com");
        Product productUpdated =  repository.save(product);

        //Verify
        System.out.println(productUpdated);
        Assertions.assertThat(productUpdated.getDescription()).isEqualTo("jockhuang@gmail.com");

    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteProductTest(){
        //Action
        repository.deleteById(1);
        Optional<Product> productOptional = repository.findById(1);

        //Verify
        Assertions.assertThat(productOptional).isEmpty();
    }
}