package com.course.Reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addReader(Reader reader) {
        String sql = "INSERT INTO Reader (Name, Surname) VALUES (?, ?)";
        jdbcTemplate.update(sql, reader.getName(), reader.getSurname());
    }

    public List<Reader> getAllReaders() {
        String sql = "SELECT * FROM Reader";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Reader.class));
    }

    public Reader getReaderById(int readerId) {
        String sql = "SELECT * FROM Reader WHERE Id_reader = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Reader.class), readerId);
    }

    public void updateReader(Reader reader) {
        String sql = "UPDATE Reader SET Name = ?, Surname = ? WHERE Id_reader = ?";
        jdbcTemplate.update(sql, reader.getName(), reader.getSurname(), reader.getId());
    }

    public void deleteReader(int readerId) {
        String sql = "DELETE FROM Reader WHERE Id_reader = ?";
        jdbcTemplate.update(sql, readerId);
    }
}