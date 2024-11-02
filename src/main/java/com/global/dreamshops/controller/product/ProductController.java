package com.global.dreamshops.controller.product;

import com.global.dreamshops.exception.ResourceNotFoundException;
import com.global.dreamshops.model.Product;
import com.global.dreamshops.request.AddProductRequest;
import com.global.dreamshops.request.ProductUpdateRequest;
import com.global.dreamshops.response.ApiResponse;
import com.global.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        try {
            return ResponseEntity.ok(new ApiResponse ("Success",productService.getAllProducts()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());        }
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse ("Success",product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product newProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse ("Add Product Success",newProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateRequest product,@PathVariable Long id) {
        try {
            Product updateProduct = productService.updateProduct(id,product);
            return ResponseEntity.ok(new ApiResponse ("Update Product Success",updateProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse ("Delete Product Success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/name-and-brand/{name}/{brand}")
    public ResponseEntity<?> getProductsByBrandAndName(@PathVariable String brand,@PathVariable String name){
        try {
            List<Product> products = productService.getProductsByBrandAndName(brand,name);
            if(products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found with name and brand",null));

            }
            return ResponseEntity.ok(new ApiResponse ("Success",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }
    @GetMapping("/category-and-brand/{category}/{brand}")
    public ResponseEntity<?> getProductsByCategoryAndBrand(@PathVariable String brand,@PathVariable String category){
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(brand,category);
            if(products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found with name and category",null));

            }
            return ResponseEntity.ok(new ApiResponse ("Success",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String category){
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if(products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found with name and category",null));

            }
            return ResponseEntity.ok(new ApiResponse ("Success",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }
    @GetMapping("/brand/{brand}")
    public ResponseEntity<?> getProductsByBrand(@PathVariable String brand){
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if(products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found with name and category",null));

            }
            return ResponseEntity.ok(new ApiResponse ("Success",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getProductsByName(@PathVariable String name){
        try {
            List<Product> products = productService.getProductsByName(name);
            if(products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found with name and category",null));

            }
            return ResponseEntity.ok(new ApiResponse ("Success",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }
    @GetMapping("/count/{name}/{brand}")
    public ResponseEntity<?> countProductsByBrandAndName(@PathVariable String name,@PathVariable String brand){
        try {
            Long productsCount = productService.countProductsByBrandAndName(brand,name);
            return ResponseEntity.ok(new ApiResponse ("Products count",productsCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }
}
