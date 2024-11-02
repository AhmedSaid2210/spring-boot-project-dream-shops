package com.global.dreamshops.service.product;

import com.global.dreamshops.model.Product;
import com.global.dreamshops.request.AddProductRequest;
import com.global.dreamshops.request.ProductUpdateRequest;
import jakarta.persistence.Lob;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product addProduct(AddProductRequest product);
    Product updateProduct(Long id , ProductUpdateRequest product) ;
    void deleteProduct(Long id);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
