package com.course.Copy;

import com.course.Book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CopyService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BookService bookService;

    @Transactional
    public void addCopy(String bookTitle) {
        int bookId = bookService.getBookIdByTitle(bookTitle);

        // Dodaj nowy egzemplarz do tabeli Copy
        String addCopyQuery = "INSERT INTO public.\"Copy\" (\"Id_book\", \"Status\") VALUES (?, 'dostępny')";
        jdbcTemplate.update(addCopyQuery, bookId);
    }

    public List<Copy> getAllCopies() {
        String sql = "SELECT * FROM public.\"Copy\"";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Copy.class));
    }

    public Copy getCopyById(int copyId) {
        String sql = "SELECT * FROM public.\"Copy\" WHERE \"Id_copy\" = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Copy.class), copyId);
    }

    public void updateCopy(Copy copy) {
        String sql = "UPDATE public.\"Copy\" SET Id_book = ?, Status = ? WHERE Id_copy = ?";
        jdbcTemplate.update(sql, copy.getBookId(), copy.getStatus(), copy.getId());
    }

    public void deleteCopy(int copyId) {
        String sql = "DELETE FROM public.\"Copy\" WHERE \"Id_copy\" = ?";
        jdbcTemplate.update(sql, copyId);
    }

    public List<CopyDetails> getBorrowedCopies() {
        String sql = "SELECT c.\"Id_copy\", b.\"Title\", b.\"Author\" " +
                "FROM public.\"Copy\" c " +
                "JOIN public.\"Book\" b ON c.\"Id_book\" = b.\"Id_book\" " +
                "WHERE c.\"Status\" = 'wypożyczony'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CopyDetails copyDetails = new CopyDetails();
            copyDetails.setId(rs.getInt("Id_copy"));
            copyDetails.setTitle(rs.getString("Title"));
            copyDetails.setAuthor(rs.getString("Author"));
            return copyDetails;
        });
    }
}