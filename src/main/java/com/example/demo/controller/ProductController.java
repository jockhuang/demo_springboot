package com.example.demo.controller;

import com.example.demo.model.MyResponse;
import com.example.demo.model.PagenatedData;
import com.example.demo.model.Product;
import com.example.demo.model.ProductReview;
import com.example.demo.service.ProductReviewService;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Resource
    private ProductService productService;
    @Resource
    private ProductReviewService productReviewService;


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

        PagenatedData<Product> result = productService.getAllProducts(search,paging);

        return MyResponse.ok(result);

    }

    @GetMapping("{productId}")
    @Operation(summary = "Retrieve one product")
    public MyResponse getProduct(@Parameter(description = "ID of product to be retrieved", required = true)
                                @PathVariable int productId) {
        Product product = productService.getProductById(productId);
        return MyResponse.ok(product);
    }

    @PostMapping("")
    @Operation(summary = "add a new product")
    public MyResponse addProduct(@RequestBody Product product) {
        Product p = productService.addProduct(product);
        return MyResponse.ok(p);
    }

    @Operation(summary = "Update an product",
            description = "Update an existing product. The response is updated Product object with id, first name, and last name.")
    @PutMapping("/{productId}")
    public MyResponse updateProduct(@PathVariable int productId, @RequestBody Product product) {
        product.setId(productId);
        Product p = productService.updateProduct(product);
        return MyResponse.ok(p);
    }


    @ApiResponses({
            @ApiResponse (responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)})
    @DeleteMapping("/{productId}")
    @Operation(summary = "delete a product via id")
    public MyResponse deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);
        return MyResponse.ok("Deleted product with id: " + productId);
    }

    @PostMapping("/review")
    @Operation(summary = "add a new review of product")
    public MyResponse addProductReview(@RequestBody ProductReview productReview) {
        ProductReview p = productReviewService.addProductReview(productReview);
        return MyResponse.ok(p);
    }

    @GetMapping("/review/{productId}")
    @Operation(summary = "get a product reviews")
    public MyResponse getProductReview(@Parameter(description = "ID of product to be retrieved", required = true)
                                 @PathVariable int productId) {
        List<ProductReview> reviews = productReviewService.getProductReviewByProductId(productId);
        return MyResponse.ok(reviews);
    }
}
