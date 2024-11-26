package com.example.demo.service;

import com.example.demo.model.ProductReview;

import java.util.List;

public interface ProductReviewService {
    ProductReview addProductReview(ProductReview productReview);

    List<ProductReview> getProductReviewByProductId(Integer productId);
}
