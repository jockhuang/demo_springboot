package com.example.demo.repository;

import com.example.demo.model.ProductReview;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductReviewRepositoryTest {
    @Resource
    ProductReviewRepository repository;

    @Test
    @DisplayName("Test 1:Add review Test")
    @Order(1)
    @Rollback(value = false)
    public void addReviewTest(){

        //Action
        ProductReview review = ProductReview.builder()
                .productId(1)
                .rating(5)
                .comment("test")
                .author("jock")
                .createdAt(Instant.now())
                .build();

        repository.addReview(review);

        //Verify
        System.out.println(review);
        assertThat(review.getId()).isNotEmpty();
    }

    @Test
    @DisplayName("Test 2:get all reviews of a product")
    @Order(2)
    @Rollback(value = false)
    public void getProductReviewByProductIdTest(){
        List<ProductReview> reviews = repository.getProductReviewByProductId(1);
        System.out.println(reviews);
        assertThat(reviews.size()).isGreaterThan(0);
    }
}