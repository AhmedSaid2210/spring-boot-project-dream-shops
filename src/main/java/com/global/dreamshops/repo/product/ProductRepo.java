package com.global.dreamshops.repo.product;

import com.global.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByCategoryName(String name);
    List<Product> findByBrand(String brand);
    List<Product> findByCategoryNameAndBrand(String categoryName, String brand);
    List<Product> findByName(String name);
    List<Product> findByNameAndBrand(String name, String brand);
    Long countByNameAndBrand(String name, String brand);
}
