package com.example.productservice.projections;

import lombok.Getter;

@Getter
public class ProductWithTitleAndPriceDto {
    private String title;
    private Double price;

    public ProductWithTitleAndPriceDto(String title, Double price) {
        this.title = title;
        this.price = price;
    }
}

