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

    public void addReader(String name, String surname, String phone) {
        String insertReaderSql = "INSERT INTO public.\"reader\" (\"name\", \"surname\") VALUES (?, ?)";
        jdbcTemplate.update(insertReaderSql, name, surname);

        int id_reader = jdbcTemplate.queryForObject("SELECT lastval()", Integer.class);

        String insertContactSql = "INSERT INTO public.\"contact\" (\"id_reader\", \"phone\") VALUES (?, ?)";
        jdbcTemplate.update(insertContactSql,id_reader, phone);
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
    public void deleteReader(String surname) {
        String deleteRentsSql = "DELETE FROM public.\"rent\" WHERE \"id_reader\" IN (SELECT \"id_reader\" FROM public.\"reader\" WHERE \"surname\" = ?)";
        jdbcTemplate.update(deleteRentsSql, surname);

        String deleteContactSql = "DELETE FROM public.\"contact\" WHERE \"id_reader\" IN (SELECT \"id_reader\" FROM public.\"reader\" WHERE \"surname\" = ?)";
        jdbcTemplate.update(deleteContactSql, surname);

        String deleteReaderSql = "DELETE FROM public.\"reader\" WHERE \"surname\" = ?";
        jdbcTemplate.update(deleteReaderSql, surname);
    }

    public List<ReaderInfo> getReadersBySurname(String surname) {
        String sql = "SELECT r.*, c.\"phone\" AS phone_number " +
                "FROM public.\"reader\" r " +
                "LEFT JOIN public.\"contact\" c ON r.\"id_reader\" = c.\"id_reader\" " +
                "WHERE r.\"surname\" = ? ";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, surname);
        return mapReadersInfo(rows);
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

    private List<ReaderInfo> mapReadersInfo(List<Map<String, Object>> rows) {
        List<ReaderInfo> readers = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            ReaderInfo reader = new ReaderInfo();
            reader.setId((Integer) row.get("Id_reader"));
            reader.setName((String) row.get("Name"));
            reader.setSurname((String) row.get("Surname"));
            reader.setPhone((String) row.get("phone_number"));
            readers.add(reader);
        }
        return readers;
    }
}

