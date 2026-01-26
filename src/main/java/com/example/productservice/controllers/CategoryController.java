package com.example.productservice.controllers;

import com.example.productservice.models.Category;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @GetMapping("/{categoryId}")
    public Category getCategoryById(@PathVariable("categoryId") Long categoryId) {
        throw new RuntimeException();
    }
}