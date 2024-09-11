package com.example.demo.service;

import com.example.demo.model.PagenatedData;
import com.example.demo.model.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    public Product getProductById(Integer id);
    public PagenatedData<Product> getAllProducts(String filter, Pageable paging);
    public Product addProduct(Product product);
    public Product updateProduct(Product product);
    public void deleteProduct(Integer id);
}
