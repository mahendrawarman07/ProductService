package com.example.productservice;

import com.example.productservice.models.Product;
import com.example.productservice.projections.ProductWithTitleAndPrice;
import com.example.productservice.projections.ProductWithTitleAndPriceDto;
import com.example.productservice.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductServiceApplicationTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
    }


    @Test
    public void testQuery() {
        List<ProductWithTitleAndPrice> productWithTitleAndPrices =
                productRepository.findTitleAndPriceById();

        System.out.println(productWithTitleAndPrices);
        for (ProductWithTitleAndPrice productWithTitleAndPrice : productWithTitleAndPrices) {
            System.out.println(productWithTitleAndPrice.getTitle() + " " + productWithTitleAndPrice.getPrice());
        }

//        Optional<Product> optionalProduct = productRepository.findByCategory_Title("Mobiles");

//        System.out.println(optionalProduct.get().getPrice());

        List<Object[]> list1 = productRepository.findTitleAndPriceById(1L);
        System.out.println(list1.get(0)[0] + " " + list1.get(0)[1]);

        ProductWithTitleAndPriceDto product = productRepository.findTitleAndPriceByIdWithRange(3L);
        System.out.println(product.getTitle() + " " + product.getPrice());
    }

}