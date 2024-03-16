package com.course.jdbc.category;

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
        String sql = "INSERT INTO public.\"category\" (Name) VALUES (?)";
        jdbcTemplate.update(sql, category.getName());
    }

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM public.\"category\"";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    public Category getCategoryById(int categoryId) {
        String sql = "SELECT * FROM public.\"category\" WHERE \"id_category\" = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), categoryId);
    }

    public void deleteCategory(int categoryId) {
        String sql = "DELETE FROM public.\"category\" WHERE \"id_category\" = ?";
        jdbcTemplate.update(sql, categoryId);
    }
}