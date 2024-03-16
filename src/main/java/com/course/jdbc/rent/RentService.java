package com.course.jdbc.rent;

import com.course.jdbc.reader.Reader;
import org.springframework.beans.factory.annotation.Autowired;
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
        String checkCopyAvailabilitySql = "SELECT \"status\" FROM public.\"copy\" WHERE \"id_copy\" = ?";
        String status = jdbcTemplate.queryForObject(checkCopyAvailabilitySql, new Object[]{copyId}, String.class);
        if (!"dostępny".equals(status)) {
            throw new Exception("Egzemplarz nie jest dostępny do wypożyczenia.");
        }

        // Aktualizacja statusu egzemplarza na "wypożyczony"
        String updateCopyStatusSql = "UPDATE public.\"copy\" SET \"status\" = 'wypożyczony' WHERE \"id_copy\" = ?";
        jdbcTemplate.update(updateCopyStatusSql, copyId);

        // Ustalenie bieżącej daty
        Date currentDate = Date.valueOf(LocalDate.now());

        // Dodanie wypożyczenia
        String addRentSql = "INSERT INTO public.\"rent\" (\"date_rent\", \"id_copy\", \"id_reader\") VALUES (?, ?, ?)";
        jdbcTemplate.update(addRentSql, currentDate, copyId, readerId);
    }

    public List<RentDetail> getAllRents() {
        String sql = "SELECT r.*, c.*, rd.* " +
                "FROM public.\"rent\" r " +
                "JOIN public.\"copy\" c ON r.\"id_copy\" = c.\"id_copy\" " +
                "JOIN public.\"reader\" rd ON r.\"id_reader\" = rd.\"id_reader\"";
        return jdbcTemplate.query(sql, rentDetailRowMapper);
    }

    public RentDetail getRentById(int rentId) {
        String sql = "SELECT r.*, c.*, rd.* " +
                "FROM public.\"rent\" r " +
                "JOIN public.\"copy\" c ON r.\"id_copy\" = c.\"id_copy\" " +
                "JOIN public.\"reader\" rd ON r.\"id_reader\" = rd.\"id_reader\" " +
                "WHERE r.\"id_rent\" = ?";
        return jdbcTemplate.queryForObject(sql, rentDetailRowMapper, rentId);
    }

    @Transactional
    public void updateRent(int copyId) throws Exception {
        // Sprawdzenie, czy egzemplarz jest aktualnie wypożyczony
        String checkRentSql = "SELECT \"id_rent\" FROM public.\"rent\" WHERE \"id_copy\" = ? AND \"date_return\" IS NULL";
        Integer rentId = jdbcTemplate.queryForObject(checkRentSql, new Object[]{copyId}, Integer.class);
        if (rentId == null) {
            throw new Exception("Egzemplarz nie jest aktualnie wypożyczony.");
        }

        Date currentDate = Date.valueOf(LocalDate.now());
        String updateRentSql = "UPDATE public.\"rent\" SET \"date_return\" = ? WHERE \"id_rent\" = ?";
        jdbcTemplate.update(updateRentSql, currentDate, rentId);

        String updateCopyStatusSql = "UPDATE public.\"copy\" SET \"status\" = 'dostępny' WHERE \"id_copy\" = ?";
        jdbcTemplate.update(updateCopyStatusSql, copyId);
    }

    public void deleteRent(int rentId) {
        String sql = "DELETE FROM public.\"rent\" WHERE \"id_rent\" = ?";
        jdbcTemplate.update(sql, rentId);
    }

    private final RowMapper<RentDetail> rentDetailRowMapper = (rs, rowNum) -> {
        RentDetail rentDetail = new RentDetail();
        rentDetail.setId(rs.getInt("id_rent"));
        rentDetail.setDateRent(rs.getDate("date_rent"));
        rentDetail.setDateReturn(rs.getDate("date_return"));
        rentDetail.setCopyId(rs.getInt("id_copy"));

        Reader reader = new Reader();
        reader.setId(rs.getInt("id_reader"));
        reader.setName(rs.getString("name"));
        reader.setSurname(rs.getString("surname"));

        rentDetail.setReader(reader);

        return rentDetail;
    };

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
        String sql = "SELECT * FROM public.\"rent\" WHERE \"id_reader\" = ?";
        return jdbcTemplate.query(sql, rentRowMapper, readerId);
    }
}
