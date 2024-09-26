package com.example.demo.repository;

import com.example.demo.model.ProductReview;

import java.util.List;

public interface ProductReviewRepository {
    List<ProductReview> getProductReviewByProductId(Integer productId);
    ProductReview addReview(ProductReview productReview);
}
