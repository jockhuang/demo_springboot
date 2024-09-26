package com.example.demo.service.impl;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1)
                .name("Jock's System")
                .description("Unit test")
                .imageURL("test")
                .price(10.1)
                .createDate(Instant.now())
                .updateDate(Instant.now())
                .isRelease(true)
                .build();
    }
    @Test
    @Order(1)
    void addProduct() {
        // precondition
        given(repository.save(product)).willReturn(product);

        //action
        Product savedproduct = productService.addProduct(product);

        // verify the output
        System.out.println(savedproduct);
        assertThat(savedproduct).isNotNull();
    }

    @Test
    @Order(2)
    void getProductById() {
        // precondition
        given(repository.findById(1)).willReturn(of(product));

        // action
        Product existingProduct = productService.getProductById(product.getId());

        // verify
        System.out.println(existingProduct);
        assertThat(existingProduct).isNotNull();
    }

    @Test
    @Order(3)
    void getAllProducts() {
        Product product2 = Product.builder()
                .id(2)
                .name("Jock's System1")
                .description("Unit test")
                .imageURL("test")
                .price(10.1)
                .createDate(Instant.now())
                .updateDate(Instant.now())
                .isRelease(true)
                .build();

        // precondition
        Page<Product> products = new Page<Product>() {

            @Override
            public Iterator<Product> iterator() {
                return null;
            }

            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 2;
            }

            @Override
            public int getNumber() {
                return 2;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 2;
            }

            @Override
            public List<Product> getContent() {
                return List.of(product, product2);
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public <U> Page<U> map(Function<? super Product, ? extends U> converter) {
                return null;
            }
        };

        given(repository.findByNameContaining(anyString(),any())).willReturn(products);

        // action
        Pageable paging = PageRequest.of(1, 10);
        products = repository.findByNameContaining("Jock", paging);
        // verify
        System.out.println(products);
        assertThat(products).isNotNull();
    }

    @Test
    @Order(4)
    void updateProduct() {
        // precondition
        given(repository.findById(product.getId())).willReturn(Optional.of(product));
        product.setDescription("jock@gmail.com");
        product.setName("Test");
        given(repository.save(product)).willReturn(product);

        // action
        Product updatedProduct = productService.updateProduct(product);

        // verify
        System.out.println(updatedProduct);
        assertThat(updatedProduct.getDescription()).isEqualTo("jock@gmail.com");
        assertThat(updatedProduct.getName()).isEqualTo("Test");
    }

    @Test
    @Order(5)
    void deleteProduct() {
        // precondition
        willDoNothing().given(repository).deleteById(product.getId());

        // action
        productService.deleteProduct(product.getId());

        // verify
        verify(repository, times(1)).deleteById(product.getId());
    }
}