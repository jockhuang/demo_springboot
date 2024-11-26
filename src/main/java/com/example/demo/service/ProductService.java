package com.example.demo.service;

import com.example.demo.model.PagenatedData;
import com.example.demo.model.Product;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product getProductById(Integer id);

    PagenatedData<Product> getAllProducts(String filter, Pageable paging);

    Product addProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Integer id);
}
