package com.course.jdbc.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void addBook(String title, String author, String categoryName) {
        String categoryIdQuery = "SELECT \"id_category\" FROM public.\"category\" WHERE \"name\" = ?";
        Integer categoryId = jdbcTemplate.queryForObject(categoryIdQuery, Integer.class, categoryName);

        String addBookQuery = "INSERT INTO public.\"book\" (\"title\", \"author\", \"id_category\") VALUES (?, ?, ?)";
        jdbcTemplate.update(addBookQuery, title, author, categoryId);
    }

    public List<BookDetails> getAllBooks() {
        String sql = "SELECT b.\"title\", b.\"author\", c.\"name\" AS category, " +
                "COUNT(*) AS total_copies, " +
                "SUM(CASE WHEN co.\"status\" = 'dostępny' THEN 1 ELSE 0 END) AS available_copies " +
                "FROM public.\"book\" b " +
                "JOIN public.\"category\" c ON b.\"id_category\" = c.\"id_category\" " +
                "LEFT JOIN public.\"copy\" co ON b.\"id_book\" = co.\"id_book\" " +
                "GROUP BY b.\"title\", b.\"author\", c.\"name\"";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapBookDetailsFromResultSet(rs));
    }

    public Book getBookById(Integer bookId) {
        String sql = "SELECT * FROM public.\"book\" WHERE \"id_book\" = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), bookId);
    }

    public List<BookDetails> getBooksByAuthor(String author) {
        String sql = "SELECT b.\"title\", b.\"author\", c.\"name\" AS category, " +
                "COUNT(*) AS total_copies, " +
                "SUM(CASE WHEN co.\"status\" = 'dostępny' THEN 1 ELSE 0 END) AS available_copies " +
                "FROM public.\"book\" b " +
                "JOIN public.\"category\" c ON b.\"id_category\" = c.\"id_category\" " +
                "LEFT JOIN public.\"copy\" co ON b.\"id_book\" = co.\"id_book\" " +
                "WHERE b.\"author\" = ? " +
                "GROUP BY b.\"title\", b.\"author\", c.\"name\"";
        return jdbcTemplate.query(sql, new Object[]{author}, (rs, rowNum) -> mapBookDetailsFromResultSet(rs));
    }

    public BookDetails getBooksByTitle(String title) {
        String sql = "SELECT b.\"title\", b.\"author\", c.\"name\" AS category, " +
                "COUNT(*) AS total_copies, " +
                "SUM(CASE WHEN co.\"status\" = 'dostępny' THEN 1 ELSE 0 END) AS available_copies " +
                "FROM public.\"book\" b " +
                "JOIN public.\"category\" c ON b.\"id_category\" = c.\"id_category\" " +
                "LEFT JOIN public.\"copy\" co ON b.\"id_book\" = co.\"id_book\" " +
                "WHERE b.\"title\" = ? " +
                "GROUP BY b.\"title\", b.\"author\", c.\"name\"";
        return jdbcTemplate.queryForObject(sql, new Object[]{title}, (rs, rowNum) -> mapBookDetailsFromResultSet(rs));
    }

    public List<BookDetails> getBooksForCategory(String categoryName) {
        String sql = "SELECT b.\"title\", b.\"author\", c.\"name\" AS category, " +
                "COUNT(*) AS total_copies, " +
                "SUM(CASE WHEN co.\"status\" = 'dostępny' THEN 1 ELSE 0 END) AS available_copies " +
                "FROM public.\"book\" b " +
                "JOIN public.\"category\" c ON b.\"id_category\" = c.\"id_category\" " +
                "LEFT JOIN public.\"copy\" co ON b.\"id_book\" = co.\"id_book\" " +
                "WHERE c.\"name\" = ? " +
                "GROUP BY b.\"title\", b.\"author\", c.\"name\"";
        return jdbcTemplate.query(sql, new Object[]{categoryName}, (rs, rowNum) -> mapBookDetailsFromResultSet(rs));
    }

    private BookDetails mapBookDetailsFromResultSet(ResultSet rs) throws SQLException {
        BookDetails bookDetails = new BookDetails();
        bookDetails.setTitle(rs.getString("Title"));
        bookDetails.setAuthor(rs.getString("Author"));
        bookDetails.setCategory(rs.getString("category"));
        bookDetails.setTotalCopies(rs.getInt("total_copies"));
        bookDetails.setAvailableCopies(rs.getInt("available_copies"));
        return bookDetails;
    }

    public int getBookIdByTitle(String bookTitle) {
        String sql = "SELECT \"id_book\" FROM public.\"book\" WHERE \"title\" = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, bookTitle);
    }
}