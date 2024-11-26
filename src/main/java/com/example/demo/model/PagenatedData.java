package com.example.demo.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PagenatedData<T> {
    private long totalCount;
    private int pageIndex;
    private int pageSize;
    private long totalPage;
    private List<T> items;
    public PagenatedData(List<T> items, int pageIndex, int pageSize, long totalCount, long totalPage) {
        this.items = items;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = totalPage;


    }
}
