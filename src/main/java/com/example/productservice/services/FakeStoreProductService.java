package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service("fakeStoreProductService")
@Primary
public class FakeStoreProductService implements ProductService {
    private RestTemplate restTemplate;
    private RedisTemplate<String, Object> redisTemplate;

    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> responseEntity =
                restTemplate.getForEntity(
                        "https://fakestoreapi.com/products",
                        FakeStoreProductDto[].class
                );

        FakeStoreProductDto[] fakeStoreProductDtos = responseEntity.getBody();

        return fakeStoreProductDtos != null ?
                java.util.Arrays.stream(fakeStoreProductDtos)
                        .map((product)->convertFakeStoreProductDtoToProduct(product))
                        .toList()
                : List.of();
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        //make a http call to fakestore api to get the product with the given id.
        // https://fakestoreapi.com/products/1

        //        throw new RuntimeException("Something went wrong");

        //First check if the product with the Id is present in the cache or not.
        Product product = (Product) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCT_" + productId);

        if (product != null) {
            //Cache HIT
            return product;
        }

        //Cache MISS
        ResponseEntity<FakeStoreProductDto> responseEntity =
                restTemplate.getForEntity(
                        "https://fakestoreapi.com/products/" + productId,
                        FakeStoreProductDto.class
                );

//        return convertFakeStoreProductDtoToProduct(responseEntity.getBody());

        FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();

        if (fakeStoreProductDto == null) {
            //Invalid productId.
            throw new ProductNotFoundException(productId);
        }

        product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);

        //Before returning the product, store it redis.
        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_" + productId, product);

        return product;

//        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        return null;
    }


    public Page<Product> getProductsByTitle(String title, int pageNumber, int pageSize) {
        return null;
    }

    private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto fakeStoreProductDto) {
//        if (fakeStoreProductDto == null) {
//            return null;
//        }

        Product product = new Product();
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setTitle(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        product.setId(fakeStoreProductDto.getId());

        return product;
    }
}