package com.global.dreamshops.service.product;

import com.global.dreamshops.exception.ProductNotFoundException;
import com.global.dreamshops.model.Category;
import com.global.dreamshops.model.Product;
import com.global.dreamshops.repo.category.CategoryRepo;
import com.global.dreamshops.repo.product.ProductRepo;
import com.global.dreamshops.request.AddProductRequest;
import com.global.dreamshops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product addProduct(AddProductRequest product) {
        Category category = categoryRepo.findByName(product.getCategory().getName());
        if(category == null) {
            Category category1 = new Category(product.getCategory().getName());
            log.info("category added");
             categoryRepo.save(category1);
            category = category1;
        }
        return productRepo.save(createProduct(product,category));
    }

    private Product createProduct(AddProductRequest product, Category category) {
        return new Product(
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                category
        );
    }

    @Override
    public Product updateProduct(Long id , ProductUpdateRequest product) {
        return productRepo.findById(id)
                .map(existingProduct -> updateExistingProduct(product,existingProduct))
                .map(productRepo ::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
    private Product updateExistingProduct(ProductUpdateRequest product, Product existingProduct) {
        existingProduct.setName(product.getName());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setInventory(product.getInventory());
        existingProduct.setDescription(product.getDescription());
        Category category = categoryRepo.findByName(product.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepo.findById(id)
                .ifPresentOrElse(productRepo::delete ,
                        () -> {throw new  ProductNotFoundException("Product not found");});
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepo.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepo.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepo.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepo.findByNameAndBrand(name, brand);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepo.countByNameAndBrand(name, brand);
    }
}
