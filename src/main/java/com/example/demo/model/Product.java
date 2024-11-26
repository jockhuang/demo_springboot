package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@ToString
@Entity
@Table(name = "Products", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "Name", nullable = false)
    private String name;

    @Lob
    @Column(name = "Description")
    private String description;

    @Lob
    @Column(name = "ImageURL")
    private String imageURL;

    @Column(name = "Price")
    private Double price;

    @NotNull
    @Column(name = "IsRelease", nullable = false)
    @Builder.Default
    private Boolean isRelease = false;

    @Column(name = "CreateDate")
    private Instant createDate;

    @Column(name = "UpdateDate")
    private Instant updateDate;

}