package com.course.Reader;

import com.course.Book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReaderService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addReader(Reader reader) {
        String sql = "INSERT INTO public.\"Reader\" (Name, Surname) VALUES (?, ?)";
        jdbcTemplate.update(sql, reader.getName(), reader.getSurname());
    }

    public List<Reader> getAllReaders() {
        String sql = "SELECT * FROM public.\"Reader\"";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Reader.class));
    }

    public Reader getReaderById(int readerId) {
        String sql = "SELECT * FROM public.\"Reader\" WHERE \"Id_reader\" = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Reader.class), readerId);
    }

    public void updateReader(Reader reader) {
        String sql = "UPDATE public.\"Reader\" SET Name = ?, Surname = ? WHERE \"Id_reader\" = ?";
        jdbcTemplate.update(sql, reader.getName(), reader.getSurname(), reader.getId());
    }

    public void deleteReader(int readerId) {
        String sql = "DELETE FROM public.\"Reader\" WHERE \"Id_reader\" = ?";
        jdbcTemplate.update(sql, readerId);
    }

    public List<Reader> getReadersBySurname(String surname) {
        String sql = "SELECT * FROM public.\"Reader\" WHERE \"Surname\" = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, surname);
        return mapReadersBySurname(rows);
    }

    private List<Reader> mapReadersBySurname(List<Map<String, Object>> rows) {
        List<Reader> readers = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Reader reader = new Reader();
            reader.setId((Integer) row.get("Id_reader"));
            reader.setName((String) row.get("Name"));
            reader.setSurname((String) row.get("Surname"));
            readers.add(reader);
        }
        return readers;
    }
}