package com.course.Category;

import java.util.List;

public interface CategoryRepository {
    void addCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    void updateCategory(Category category);
    void deleteCategory(int id);
}

