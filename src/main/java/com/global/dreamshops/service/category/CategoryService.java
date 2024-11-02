package com.global.dreamshops.service.category;

import com.global.dreamshops.exception.AlreadyExistsException;
import com.global.dreamshops.exception.ResourceNotFoundException;
import com.global.dreamshops.model.Category;
import com.global.dreamshops.repo.category.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepo categoryRepo;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        return Optional.of(category)
                .filter(c -> !categoryRepo.existsByName(c.getName()))
                .map(categoryRepo::save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + "already exists"));
    }

    @Override
    public Category updateCategory(Category category,Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(updateCategory ->{
                    updateCategory.setName(category.getName());
                    return categoryRepo.save(updateCategory);
                })
                .orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete,
                ()->{
                        throw new ResourceNotFoundException("Category not found");
                });
    }
}
