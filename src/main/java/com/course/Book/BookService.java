package com.course.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addBook(Book book) {
        String sql = "INSERT INTO Book (Id_category, Title, Author) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, book.getCategoryId(), book.getTitle(), book.getAuthor());
    }

    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM Book";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBookById(int bookId) {
        String sql = "SELECT * FROM Book WHERE Id_book = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), bookId);
    }

    public void updateBook(Book book) {
        String sql = "UPDATE Book SET Id_category = ?, Title = ?, Author = ? WHERE Id_book = ?";
        jdbcTemplate.update(sql, book.getCategoryId(), book.getTitle(), book.getAuthor(), book.getId());
    }

    public void deleteBook(int bookId) {
        String sql = "DELETE FROM Book WHERE Id_book = ?";
        jdbcTemplate.update(sql, bookId);
    }
}