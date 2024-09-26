package com.example.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document("ProductReview")
public class ProductReview {
    @Id
    private String id;
    private Integer productId;
    private Integer rating;
    private String author;
    private String comment;
    private Instant createdAt;
}


