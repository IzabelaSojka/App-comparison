package com.course.Copy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addCopy(Copy copy) {
        String sql = "INSERT INTO Copy (Id_book, Status) VALUES (?, ?)";
        jdbcTemplate.update(sql, copy.getBookId(), copy.getStatus());
    }

    public List<Copy> getAllCopies() {
        String sql = "SELECT * FROM Copy";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Copy.class));
    }

    public Copy getCopyById(int copyId) {
        String sql = "SELECT * FROM Copy WHERE Id_copy = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Copy.class), copyId);
    }

    public void updateCopy(Copy copy) {
        String sql = "UPDATE Copy SET Id_book = ?, Status = ? WHERE Id_copy = ?";
        jdbcTemplate.update(sql, copy.getBookId(), copy.getStatus(), copy.getId());
    }

    public void deleteCopy(int copyId) {
        String sql = "DELETE FROM Copy WHERE Id_copy = ?";
        jdbcTemplate.update(sql, copyId);
    }
}