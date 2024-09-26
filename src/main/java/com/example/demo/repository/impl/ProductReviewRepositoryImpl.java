package com.example.demo.repository.impl;

import com.example.demo.model.ProductReview;
import com.example.demo.repository.ProductReviewRepository;
import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class ProductReviewRepositoryImpl implements ProductReviewRepository {
    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public List<ProductReview> getProductReviewByProductId(Integer productId) {
        return mongoTemplate.query(ProductReview.class).matching(query(where("productId").is(productId))).all();
    }

    @Override
    public ProductReview addReview(ProductReview productReview) {
        productReview.setId(null);
        productReview.setCreatedAt(Instant.now());
        return mongoTemplate.insert(productReview);
    }

}
