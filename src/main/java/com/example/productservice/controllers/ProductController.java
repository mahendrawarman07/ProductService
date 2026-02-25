package com.example.productservice.controllers;

import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

// localhost:8080/products
@RestController
@RequestMapping("/products") // endpoint - /products
public class ProductController {
    private ProductService productService;

    public ProductController(@Qualifier("selfProductService") ProductService productService) {
        this.productService = productService;
    }

    // localhost:8080/products/1
    @GetMapping("/{productId}")
    public Product getSingleProduct(@PathVariable("productId") Long productId) throws ProductNotFoundException {
//        try {
//            Product product = productService.getSingleProduct(productId);
//
//            return new ResponseEntity<>(
//                    product,
//                    HttpStatus.OK
//            );
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(
//                    null,
//                    HttpStatus.INTERNAL_SERVER_ERROR
//            );
//        }


//        System.out.println("DEBUG");
//        Product product = productService.getSingleProduct(productId); // @16640
//
//        Product p = new Product(); // @16736
//        p.setId(productId);
//        p.setTitle("iPhone 14 pro max.");
//        p.setDescription("iPhone 14 pro max.");
//        p.setPrice(130000.0);
//
//        return p;


        return productService.getSingleProduct(productId);

        // HTTPStatus Code - 200, 404, 403, 500, 429, ....
    }

    // localhost:8080/products
    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // localhost:8080/products
    @PostMapping()
    public Product createProduct(@RequestBody Product product) {
//        System.out.println("Product received: " + product.getId());
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long productId, @RequestBody Product product) {
        return null;
    }

//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<String> handleProductNotFoundException() {
//        return new ResponseEntity<>(
//                "Product not found, please try again",
//                HttpStatus.NOT_FOUND
//        );
//    }
}