package com.example.demo.service.impl;

import com.example.demo.model.ProductReview;
import com.example.demo.repository.ProductReviewRepository;
import com.example.demo.service.ProductReviewService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    @Resource
    private ProductReviewRepository repository;

    @Override
    public ProductReview addProductReview(ProductReview productReview) {
        return repository.addReview(productReview);
    }

    @Override
    public List<ProductReview> getProductReviewByProductId(Integer productId) {
        return repository.getProductReviewByProductId(productId);
    }
}
