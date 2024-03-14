package com.course.Rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addRent(Rent rent) {
        String sql = "INSERT INTO public.\"Rent\" (Date_rent, Date_return, Id_copy, Id_reader) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, rent.getDateRent(), rent.getDateReturn(), rent.getCopyId(), rent.getReaderId());
    }

    public List<Rent> getAllRents() {
        String sql = "SELECT * FROM public.\"Rent\"";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Rent.class));
    }

    public Rent getRentById(int rentId) {
        String sql = "SELECT * FROM public.\"Rent\" WHERE \"Id_rent\" = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Rent.class), rentId);
    }

    public void updateRent(Rent rent) {
        String sql = "UPDATE public.\"Rent\" SET Date_rent = ?, Date_return = ?, Id_copy = ?, Id_reader = ? WHERE Id_rent = ?";
        jdbcTemplate.update(sql, rent.getDateRent(), rent.getDateReturn(), rent.getCopyId(), rent.getReaderId(), rent.getId());
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
