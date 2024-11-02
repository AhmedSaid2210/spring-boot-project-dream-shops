package com.global.dreamshops.service.category;

import com.global.dreamshops.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category saveCategory(Category category);
    Category updateCategory(Category category,Long id);
    void deleteCategory(Long id);
}
