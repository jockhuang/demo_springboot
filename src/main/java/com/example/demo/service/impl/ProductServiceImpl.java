package com.example.demo.service.impl;

import com.example.demo.model.PagenatedData;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    ProductRepository repository;

    @Cacheable(value = "myCache", key = "'product_' + #productId")
    @Override
    public Product getProductById(Integer productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product id not found - " + productId));
    }

    @Override
    public PagenatedData<Product> getAllProducts(String filter, Pageable paging) {
        Page<Product> pageTuts;
        if (filter == null)
            pageTuts = repository.findAll(paging);
        else
            pageTuts = repository.findByNameContaining(filter, paging);

        return new PagenatedData<>(pageTuts.getContent(),pageTuts.getNumber()+1, paging.getPageSize(),pageTuts.getTotalElements(),pageTuts.getTotalPages());
    }

    @Cacheable(value = "myCache", key = "'product_' + #product.id")
    @Override
    public Product addProduct(Product product) {
        product.setId(0);
        Instant now = Instant.now();
        product.setCreateDate(now);
        product.setUpdateDate(now);
        repository.save(product);
        return product;
    }

    @Cacheable(value = "myCache", key = "'product_' + #product.id")
    @Override
    public Product updateProduct(Product product) {
        Product dbProduct = repository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Product id not found - " + product.getId()));
        dbProduct.setName(product.getName());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setImageURL(product.getImageURL());
        dbProduct.setIsRelease(product.getIsRelease());
        Instant now = Instant.now();
        dbProduct.setUpdateDate(now);
        repository.save(dbProduct);
        return dbProduct;
    }

    @CacheEvict(value = "myCache", key = "'product_' + #productId")
    @Override
    public void deleteProduct(Integer productId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product id not found - " + productId));
        repository.delete(product);
    }
}
