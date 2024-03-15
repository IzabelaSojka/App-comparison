package com.course.Reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReaderService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addReader(String name, String surname) {
        String sql = "INSERT INTO public.\"Reader\" (\"Name\", \"Surname\") VALUES (?, ?)";
        jdbcTemplate.update(sql, name, surname);
    }

    public List<ReaderRents> getAllReaders() {
        String sql = "SELECT r.*, " +
                "COALESCE(COUNT(rt.\"Id_reader\"), 0) AS total_books, " +
                "COALESCE(SUM(CASE WHEN rt.\"Date_return\" IS NULL THEN 1 ELSE 0 END), 0) AS not_returned_books " +
                "FROM public.\"Reader\" r " +
                "LEFT JOIN public.\"Rent\" rt ON r.\"Id_reader\" = rt.\"Id_reader\" " +
                "GROUP BY r.\"Id_reader\"";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        return mapReaders(rows);
    }

    public Reader getReaderById(int readerId) {
        String sql = "SELECT * FROM public.\"Reader\" WHERE \"Id_reader\" = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Reader.class), readerId);
    }

    public void updateReader(Reader reader) {
        String sql = "UPDATE public.\"Reader\" SET Name = ?, Surname = ? WHERE \"Id_reader\" = ?";
        jdbcTemplate.update(sql, reader.getName(), reader.getSurname(), reader.getId());
    }

    @Transactional
    public void deleteReader(int readerId) {
        // Usunięcie wszystkich wypożyczeń czytelnika
        String deleteRentsSql = "DELETE FROM public.\"Rent\" WHERE \"Id_reader\" = ?";
        jdbcTemplate.update(deleteRentsSql, readerId);

        // Usunięcie czytelnika
        String deleteReaderSql = "DELETE FROM public.\"Reader\" WHERE \"Id_reader\" = ?";
        jdbcTemplate.update(deleteReaderSql, readerId);
    }

    public List<ReaderRents> getReadersBySurname(String surname) {
        String sql = "SELECT r.*, " +
                "COALESCE(COUNT(rt.\"Id_reader\"), 0) AS total_books, " +
                "COALESCE(SUM(CASE WHEN rt.\"Date_return\" IS NULL THEN 1 ELSE 0 END), 0) AS not_returned_books " +
                "FROM public.\"Reader\" r " +
                "LEFT JOIN public.\"Rent\" rt ON r.\"Id_reader\" = rt.\"Id_reader\" " +
                "WHERE r.\"Surname\" = ? " +
                "GROUP BY r.\"Id_reader\"";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, surname);
        return mapReaders(rows);
    }

    private List<ReaderRents> mapReaders(List<Map<String, Object>> rows) {
        List<ReaderRents> readers = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            ReaderRents reader = new ReaderRents();
            reader.setId((Integer) row.get("Id_reader"));
            reader.setName((String) row.get("Name"));
            reader.setSurname((String) row.get("Surname"));
            reader.setTotalBooks((Long) row.get("total_books"));
            reader.setNotReturnedBooks((Long) row.get("not_returned_books"));
            readers.add(reader);
        }
        return readers;
    }
}

