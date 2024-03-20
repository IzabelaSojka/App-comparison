package com.course.jdbc.reader;

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
        String sql = "INSERT INTO public.\"reader\" (\"name\", \"surname\") VALUES (?, ?)";
        jdbcTemplate.update(sql, name, surname);
    }

    public List<ReaderRents> getAllReaders() {
        String sql = "SELECT r.*, " +
                "COALESCE(COUNT(rt.\"id_reader\"), 0) AS total_books, " +
                "COALESCE(SUM(CASE WHEN rt.\"date_return\" IS NULL THEN 1 ELSE 0 END), 0) AS not_returned_books " +
                "FROM public.\"reader\" r " +
                "LEFT JOIN public.\"rent\" rt ON r.\"id_reader\" = rt.\"id_reader\" " +
                "GROUP BY r.\"id_reader\"";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        return mapReaders(rows);
    }

    public Reader getReaderById(int readerId) {
        String sql = "SELECT * FROM public.\"reader\" WHERE \"id_reader\" = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Reader.class), readerId);
    }
    @Transactional
    public void deleteReader(int readerId) {
        // Usunięcie wszystkich wypożyczeń czytelnika
        String deleteRentsSql = "DELETE FROM public.\"rent\" WHERE \"id_reader\" = ?";
        jdbcTemplate.update(deleteRentsSql, readerId);

        // Usunięcie czytelnika
        String deleteReaderSql = "DELETE FROM public.\"reader\" WHERE \"id_reader\" = ?";
        jdbcTemplate.update(deleteReaderSql, readerId);
    }

    public List<ReaderRents> getReadersBySurname(String surname) {
        String sql = "SELECT r.*, c.\"phone\" AS phone_number, " +
                "COALESCE(COUNT(rt.\"id_reader\"), 0) AS total_books, " +
                "COALESCE(SUM(CASE WHEN rt.\"date_return\" IS NULL THEN 1 ELSE 0 END), 0) AS not_returned_books " +
                "FROM public.\"reader\" r " +
                "LEFT JOIN public.\"rent\" rt ON r.\"id_reader\" = rt.\"id_reader\" " +
                "LEFT JOIN public.\"contact\" c ON r.\"id_reader\" = c.\"Id_reader\" " +
                "WHERE r.\"surname\" = ? " +
                "GROUP BY r.\"id_reader\", c.\"phone\"";
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
            reader.setPhone((String) row.get("phone_number"));
            reader.setTotalBooks((Long) row.get("total_books"));
            reader.setNotReturnedBooks((Long) row.get("not_returned_books"));
            readers.add(reader);
        }
        return readers;
    }
}

