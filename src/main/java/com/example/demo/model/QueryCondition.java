package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryCondition {
    private String search;

    private String orderBy;

    private boolean isDesc;

    private int pageIndex;

    private int pageSize;
}
