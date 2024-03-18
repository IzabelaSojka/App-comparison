package com.course.hibernate.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/h/categories")
public class HCategoryController {

    @Autowired
    private HCategoryService categoryService;

    @GetMapping
    public List<HCategory> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public HCategory getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public HCategory saveCategory(@RequestBody HCategory Category) {
        return categoryService.saveCategory(Category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }

}