package com.course.Rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class RentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void addRent(int readerId, int copyId) throws Exception {
        // Sprawdzenie, czy egzemplarz jest dostępny
        String checkCopyAvailabilitySql = "SELECT \"Status\" FROM public.\"Copy\" WHERE \"Id_copy\" = ?";
        String status = jdbcTemplate.queryForObject(checkCopyAvailabilitySql, new Object[]{copyId}, String.class);
        if (!"dostępny".equals(status)) {
            throw new Exception("Egzemplarz nie jest dostępny do wypożyczenia.");
        }

        // Aktualizacja statusu egzemplarza na "wypożyczony"
        String updateCopyStatusSql = "UPDATE public.\"Copy\" SET \"Status\" = 'wypożyczony' WHERE \"Id_copy\" = ?";
        jdbcTemplate.update(updateCopyStatusSql, copyId);

        // Ustalenie bieżącej daty
        Date currentDate = Date.valueOf(LocalDate.now());

        // Dodanie wypożyczenia
        String addRentSql = "INSERT INTO public.\"Rent\" (\"Date_rent\", \"Id_copy\", \"Id_reader\") VALUES (?, ?, ?)";
        jdbcTemplate.update(addRentSql, currentDate, copyId, readerId);
    }

    public List<Rent> getAllRents() {
        String sql = "SELECT * FROM public.\"Rent\"";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Rent.class));
    }

    public Rent getRentById(int rentId) {
        String sql = "SELECT * FROM public.\"Rent\" WHERE \"Id_rent\" = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Rent.class), rentId);
    }

    @Transactional
    public void updateRent(int copyId) throws Exception {
        // Sprawdzenie, czy egzemplarz jest aktualnie wypożyczony
        String checkRentSql = "SELECT \"Id_rent\" FROM public.\"Rent\" WHERE \"Id_copy\" = ? AND \"Date_return\" IS NULL";
        Integer rentId = jdbcTemplate.queryForObject(checkRentSql, new Object[]{copyId}, Integer.class);
        if (rentId == null) {
            throw new Exception("Egzemplarz nie jest aktualnie wypożyczony.");
        }

        // Aktualizacja daty zwrotu na dzisiejszą datę
        Date currentDate = Date.valueOf(LocalDate.now());
        String updateRentSql = "UPDATE public.\"Rent\" SET \"Date_return\" = ? WHERE \"Id_rent\" = ?";
        jdbcTemplate.update(updateRentSql, currentDate, rentId);

        // Aktualizacja statusu egzemplarza na "dostępny"
        String updateCopyStatusSql = "UPDATE public.\"Copy\" SET \"Status\" = 'dostępny' WHERE \"Id_copy\" = ?";
        jdbcTemplate.update(updateCopyStatusSql, copyId);
    }

    public void deleteRent(int rentId) {
        String sql = "DELETE FROM public.\"Rent\" WHERE \"Id_rent\" = ?";
        jdbcTemplate.update(sql, rentId);
    }
    private final RowMapper<Rent> rentRowMapper = (rs, rowNum) -> {
        Rent rent = new Rent();
        rent.setId(rs.getInt("Id_rent"));
        rent.setDateRent(rs.getDate("Date_rent"));
        rent.setDateReturn(rs.getDate("Date_return"));
        rent.setCopyId(rs.getInt("Id_copy"));
        rent.setReaderId(rs.getInt("Id_reader"));
        return rent;
    };

    public List<Rent> getRentsForReader(int readerId) {
        String sql = "SELECT * FROM public.\"Rent\" WHERE \"Id_reader\" = ?";
        return jdbcTemplate.query(sql, rentRowMapper, readerId);
    }
}
