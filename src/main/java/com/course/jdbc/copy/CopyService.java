package com.course.jdbc.copy;

import com.course.jdbc.book.BookService;
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

        String addCopyQuery = "INSERT INTO public.\"copy\" (\"id_book\", \"status\") VALUES (?, 'dostępny')";
        jdbcTemplate.update(addCopyQuery, bookId);
    }

    public List<CopyDetails> getAllCopies() {
        String sql = "SELECT c.\"id_copy\", c.\"status\", b.\"id_book\", b.\"title\", b.\"author\", b.\"id_category\" " +
                "FROM public.\"copy\" c " +
                "JOIN public.\"book\" b ON c.\"id_book\" = b.\"id_book\"";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CopyDetails copyDetails = new CopyDetails();
            copyDetails.setId(rs.getInt("id_copy"));
            copyDetails.setStatus(rs.getString("status"));
            CBookDetails bookDetails = new CBookDetails();
            bookDetails.setId(rs.getInt("id_book"));
            bookDetails.setTitle(rs.getString("title"));
            bookDetails.setAuthor(rs.getString("author"));
            bookDetails.setIdCategory(rs.getInt("id_category"));

            copyDetails.setBook(bookDetails);
            return copyDetails;
        });
    }

    public Copy getCopyById(int copyId) {
        String sql = "SELECT * FROM public.\"copy\" WHERE \"id_copy\" = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Copy.class), copyId);
    }

    public void deleteCopy(int copyId) {
        String sql = "DELETE FROM public.\"copy\" WHERE \"id_copy\" = ?";
        jdbcTemplate.update(sql, copyId);
    }

    public List<CopyDetails> getBorrowedCopies() {
        String sql = "SELECT c.\"id_copy\", c.\"status\", b.\"id_book\", b.\"title\", b.\"author\", b.\"id_category\" " +
                "FROM public.\"copy\" c " +
                "JOIN public.\"book\" b ON c.\"id_book\" = b.\"id_book\" " +
                "WHERE c.\"status\" = 'wypożyczony'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CopyDetails copyDetails = new CopyDetails();
            copyDetails.setId(rs.getInt("id_copy"));
            copyDetails.setStatus(rs.getString("status"));

            CBookDetails bookDetails = new CBookDetails();
            bookDetails.setId(rs.getInt("id_book"));
            bookDetails.setTitle(rs.getString("title"));
            bookDetails.setAuthor(rs.getString("author"));
            bookDetails.setIdCategory(rs.getInt("id_category"));

            copyDetails.setBook(bookDetails);
            return copyDetails;
        });
    }
}