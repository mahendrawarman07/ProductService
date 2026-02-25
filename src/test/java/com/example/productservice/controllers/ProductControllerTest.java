package com.example.productservice.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {
    @Autowired
    private ProductController productController;

    @MockitoBean
    private ProductService productService;

    @Test
    public void testGetSingleProductPositive() throws ProductNotFoundException {
        Long productId = 10L;

        Product expectedProduct = new Product(); // @16640
        expectedProduct.setId(productId);
        expectedProduct.setTitle("iPhone 14 pro.");
        expectedProduct.setDescription("iPhone 14 pro.");
        expectedProduct.setPrice(130000.0);
        when(productService.getSingleProduct(productId))
                .thenReturn(expectedProduct);

        Product actualProduct = productController.getSingleProduct(productId);  // @16736

//        expectedProduct.getTitle(); //
//        actualProduct.getTitle(); //

        //assertEquals(expectedProduct, actualProduct, "Products are not equal.");

        assertEquals(expectedProduct.getTitle(), actualProduct.getTitle());

        assertEquals(expectedProduct.getDescription(), actualProduct.getDescription());

        assertEquals(expectedProduct.getPrice(), actualProduct.getPrice());
    }

    @Test
    public void testGetSingleProductInvalidId() throws ProductNotFoundException {
        Long productId = -1L;

        when(productService.getSingleProduct(productId))
                .thenThrow(new ProductNotFoundException());

        assertThrows(
                ProductNotFoundException.class,
                () -> productController.getSingleProduct(productId)
        );
    }

    public void testGetAllProducts() {

        Product product1 = new Product();
        /// /.....set some attrs to Product1

        Product product2 = new Product();
        /// /.....set some attrs to Product2

        Product product3 = new Product();
        /// /.....set some attrs to Product3

        List<Product> expectedProducts = List.of(product1, product2, product3);

        when(productService.getAllProducts())
                .thenReturn(expectedProducts);

        List<Product> actualProducts = productController.getAllProducts();

        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());

    }
}