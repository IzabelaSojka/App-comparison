package com.course.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.RowMapper;
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
        // Pobierz id kategorii na podstawie nazwy kategorii
        String categoryIdQuery = "SELECT \"Id_category\" FROM public.\"Category\" WHERE \"Name\" = ?";
        Integer categoryId = jdbcTemplate.queryForObject(categoryIdQuery, Integer.class, categoryName);

        // Dodaj książkę do bazy danych
        String addBookQuery = "INSERT INTO public.\"Book\" (\"Title\", \"Author\", \"Id_category\") VALUES (?, ?, ?)";
        jdbcTemplate.update(addBookQuery, title, author, categoryId);
    }

    public List<BookDetails> getAllBooks() {
        String sql = "SELECT b.\"Title\", b.\"Author\", c.\"Name\" AS category, " +
                "COUNT(*) AS total_copies, " +
                "SUM(CASE WHEN co.\"Status\" = 'dostępny' THEN 1 ELSE 0 END) AS available_copies " +
                "FROM public.\"Book\" b " +
                "JOIN public.\"Category\" c ON b.\"Id_category\" = c.\"Id_category\" " +
                "LEFT JOIN public.\"Copy\" co ON b.\"Id_book\" = co.\"Id_book\" " +
                "GROUP BY b.\"Title\", b.\"Author\", c.\"Name\"";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapBookDetailsFromResultSet(rs));
    }

    public Book getBookById(Integer bookId) {
        String sql = "SELECT * FROM public.\"Book\" WHERE \"Id_book\" = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), bookId);
    }

    public void updateBook(Book book) {
        String sql = "UPDATE public.\"Book\" SET Id_category = ?, Title = ?, Author = ? WHERE Id_book = ?";
        jdbcTemplate.update(sql, book.getCategoryId(), book.getTitle(), book.getAuthor(), book.getId());
    }

    public void deleteBook(int bookId) {
        String sql = "DELETE FROM public.\"Book\" WHERE \"Id_book\" = ?";
        jdbcTemplate.update(sql, bookId);
    }

    public List<BookDetails> getBooksByAuthor(String author) {
        String sql = "SELECT b.\"Title\", b.\"Author\", c.\"Name\" AS category, " +
                "COUNT(*) AS total_copies, " +
                "SUM(CASE WHEN co.\"Status\" = 'dostępny' THEN 1 ELSE 0 END) AS available_copies " +
                "FROM public.\"Book\" b " +
                "JOIN public.\"Category\" c ON b.\"Id_category\" = c.\"Id_category\" " +
                "LEFT JOIN public.\"Copy\" co ON b.\"Id_book\" = co.\"Id_book\" " +
                "WHERE b.\"Author\" = ? " +
                "GROUP BY b.\"Title\", b.\"Author\", c.\"Name\"";
        return jdbcTemplate.query(sql, new Object[]{author}, (rs, rowNum) -> mapBookDetailsFromResultSet(rs));
    }

    public BookDetails getBooksByTitle(String title) {
        String sql = "SELECT b.\"Title\", b.\"Author\", c.\"Name\" AS category, " +
                "COUNT(*) AS total_copies, " +
                "SUM(CASE WHEN co.\"Status\" = 'dostępny' THEN 1 ELSE 0 END) AS available_copies " +
                "FROM public.\"Book\" b " +
                "JOIN public.\"Category\" c ON b.\"Id_category\" = c.\"Id_category\" " +
                "LEFT JOIN public.\"Copy\" co ON b.\"Id_book\" = co.\"Id_book\" " +
                "WHERE b.\"Title\" = ? " +
                "GROUP BY b.\"Title\", b.\"Author\", c.\"Name\"";
        return jdbcTemplate.queryForObject(sql, new Object[]{title}, (rs, rowNum) -> mapBookDetailsFromResultSet(rs));
    }

    public final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getInt("Id_book"));
        book.setCategoryId(rs.getInt("Id_category"));
        book.setTitle(rs.getString("Title"));
        book.setAuthor(rs.getString("Author"));
        // Dodaj resztę pól zgodnie z modelem Book
        return book;
    };

    public List<BookDetails> getBooksForCategory(String categoryName) {
        String sql = "SELECT b.\"Title\", b.\"Author\", c.\"Name\" AS category, " +
                "COUNT(*) AS total_copies, " +
                "SUM(CASE WHEN co.\"Status\" = 'dostępny' THEN 1 ELSE 0 END) AS available_copies " +
                "FROM public.\"Book\" b " +
                "JOIN public.\"Category\" c ON b.\"Id_category\" = c.\"Id_category\" " +
                "LEFT JOIN public.\"Copy\" co ON b.\"Id_book\" = co.\"Id_book\" " +
                "WHERE c.\"Name\" = ? " +
                "GROUP BY b.\"Title\", b.\"Author\", c.\"Name\"";
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
        String sql = "SELECT \"Id_book\" FROM public.\"Book\" WHERE \"Title\" = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, bookTitle);
    }
}