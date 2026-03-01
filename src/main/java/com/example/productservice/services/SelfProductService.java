package com.example.productservice.services;

import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
//@Primary
public class SelfProductService implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(productId);
        }

        return optionalProduct.get();
    }

    @Override
    public Product createProduct(Product product) {
//        System.out.println("Product created: " + product.getId());
        //Validations.
//
//        if (product.getId() != null) {
//            Optional<Product> optionalProduct = productRepository.findById(product.getId());
//
//            if (optionalProduct.isEmpty()) {
//                throw new RuntimeException("Invalid Product");
//            }
//        }
        Category category = product.getCategory();
//        System.out.println("category: " + category);
        Optional<Category> optionalCategory = categoryRepository.findByTitle(category.getTitle());

        if (optionalCategory.isEmpty()) {
            //Create a category
            Category savedCategory = categoryRepository.save(category);
            product.setCategory(savedCategory);
        }else{
            product.setCategory(optionalCategory.get());
        }

        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        return null;
    }

    @Override
    public Page<Product> getProductsByTitle(String title, int pageNumber, int pageSize) {

        Sort sort = Sort.by(Sort.Direction.DESC, "price");
//                .and(Sort.by(Sort.Direction.ASC, "title"))

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        return productRepository.findByTitleContainsIgnoreCase(title, pageRequest);
    }
}