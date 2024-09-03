package com.example.demo.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryCondition {
    private String search ;

    private String orderBy;

    private boolean isDesc;

    private int pageIndex ;

    private int pageSize;
}
