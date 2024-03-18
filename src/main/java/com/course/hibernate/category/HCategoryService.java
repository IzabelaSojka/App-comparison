package com.course.hibernate.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HCategoryService {

    @Autowired
    private HCategoryRepository categoryRepository;

    public List<HCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public HCategory getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public HCategory saveCategory(HCategory HCategory) {
        return categoryRepository.save(HCategory);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

}