package com.example.productservice.repositories;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.projections.ProductWithTitleAndPrice;
import com.example.productservice.projections.ProductWithTitleAndPriceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //Declared Queries
    Optional<Product> findById(Long aLong);

    // select * from products
    List<Product> findAll();

    //select * from products where title = ?
    List<Product> findByTitle(String title);

    //select * from products where title LIKE '%str%'
//    List<Product> findByTitleContainsIgnoreCase(String str);

    //select * from products where title LIKE '%iPhone%'
    Page<Product> findByTitleContainsIgnoreCase(String str, Pageable pageable);

    // select * from products where price >= start and price <= end
    List<Product> findByPriceBetween(Double start, Double end);


    //select * from products where title LIKE '%str%' and price >= start and price <= end
    List<Product> findByTitleContainsIgnoreCaseAndPriceBetween(String title, Double start, Double end);

    List<Product> findByCreatedAtBetween(Date start, Date end);

    @Override
    void deleteById(Long aLong);

    Product save(Product product);

    //Query : Find the title and price of the product with id = 10;
    // select title, price from products where id = 10;
//    @Query(value = "select p.title, p.price from products p where p.id = 2", nativeQuery = true)
    @Query("SELECT p.title, p.price FROM products p WHERE p.id = 2")
    List<ProductWithTitleAndPrice> findTitleAndPriceById();

    @Query("SELECT p.title, p.price FROM products p WHERE p.id = :id")
    List<Object[]> findTitleAndPriceById(@Param("id") Long id);

    @Query("""
       SELECT new com.example.productservice.projections.ProductWithTitleAndPriceDto(
           p.title, p.price
       )
       FROM products p
       WHERE p.id = :id
       """)
    ProductWithTitleAndPriceDto findTitleAndPriceByIdWithRange(@Param("id") Long id);



    Optional<Product> findByCategory(Category category);

    Optional<Product> findByCategory_Title(String title);
}


//CRUD => Read & Delete
/// Create & Update