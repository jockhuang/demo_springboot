package com.example.demo.controller;

import com.example.demo.model.MyResponse;
import com.example.demo.model.PagenatedData;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    ProductRepository repository;

    @GetMapping("/products")
    @Operation(summary = "query products by sorting and pagination and filter by name.")
    public MyResponse getAllProducts(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String orderBy,
            @RequestParam(required = false, defaultValue = "true") boolean isDesc
    ) {
        pageIndex--;
        Pageable paging;
        if(orderBy!=null){
            if(isDesc){
                paging = PageRequest.of(pageIndex, pageSize, Sort.by(orderBy).descending());
            }else{
                paging = PageRequest.of(pageIndex, pageSize, Sort.by(orderBy).ascending());
            }
        }else{
            paging = PageRequest.of(pageIndex, pageSize);
        }
        Page<Product> pageTuts;
        if (search == null)
            pageTuts = repository.findAll(paging);
        else
            pageTuts = repository.findByNameContaining(search, paging);

        PagenatedData<Product> result = new PagenatedData<>(pageTuts.getContent(),pageTuts.getNumber()+1, pageSize,pageTuts.getTotalElements(),pageTuts.getTotalPages());

        return MyResponse.ok(result);

    }



    @GetMapping("{productId}")
    @Operation(summary = "Retrieve one product")
    public MyResponse getProduct(@Parameter(description = "ID of product to be retrieved", required = true)
                                @PathVariable int productId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product id not found - " + productId));
        return MyResponse.ok(product);
    }

    @PostMapping("")
    @Operation(summary = "add a new product")
    public MyResponse addProduct(@RequestBody Product product) {
        product.setId(0);
        Instant now = Instant.now();
        product.setCreateDate(now);
        product.setUpdateDate(now);
        return MyResponse.ok(repository.save(product));
    }

    @Operation(summary = "Update an product",
            description = "Update an existing product. The response is updated Product object with id, first name, and last name.")
    @PutMapping("/{productId}")
    public MyResponse updateProduct(@PathVariable int productId, @RequestBody Product product) {
        Product dbProduct = repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product id not found - " + productId));
        dbProduct.setName(product.getName());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setImageURL(product.getImageURL());
        dbProduct.setIsRelease(product.getIsRelease());
        Instant now = Instant.now();
        dbProduct.setUpdateDate(now);
        return MyResponse.ok(repository.save(dbProduct));
    }


    @ApiResponses({
            @ApiResponse (responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)})
    @DeleteMapping("/{productId}")
    @Operation(summary = "delete a product via id")
    public MyResponse deleteProduct(@PathVariable int productId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product id not found - " + productId));
        repository.delete(product);
        return MyResponse.ok("Deleted product with id: " + productId);
    }
}
