package com.course.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addCategory(Category category) {
        String sql = "INSERT INTO Category (Name) VALUES (?)";
        jdbcTemplate.update(sql, category.getName());
    }

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM Category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    public Category getCategoryById(int categoryId) {
        String sql = "SELECT * FROM Category WHERE Id_category = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), categoryId);
    }

    public void updateCategory(Category category) {
        String sql = "UPDATE Category SET Name = ? WHERE Id_category = ?";
        jdbcTemplate.update(sql, category.getName(), category.getId());
    }

    public void deleteCategory(int categoryId) {
        String sql = "DELETE FROM Category WHERE Id_category = ?";
        jdbcTemplate.update(sql, categoryId);
    }
}