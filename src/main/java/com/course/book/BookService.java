package com.course.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class BookService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addBook(Book book) {
        String sql = "INSERT INTO public.\"Book\" (Id_category, Title, Author) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, book.getCategoryId(), book.getTitle(), book.getAuthor());
    }

    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM public.\"Book\"";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class));
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

    public List<Book> getBooksByAuthor(String author) {
        String sql = "SELECT * FROM public.\"Book\" WHERE \"Author\" = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, author);
        return mapBooksByAuthor(rows);
    }

    public Book getBooksByTitle(String Title) {
        String sql = "SELECT * FROM public.\"Book\" WHERE \"Title\" = ?";
        Map<String, Object> row = jdbcTemplate.queryForMap(sql, Title);
        return mapBookByTitle(row);
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

    public List<Book> getBooksForCategory(String categoryName) {
        // Pobieramy Id_category na podstawie nazwy kategorii
        String categorySql = "SELECT \"Id_category\" FROM public.\"Category\" WHERE \"Name\" = ?";
        Integer categoryId = jdbcTemplate.queryForObject(categorySql, Integer.class, categoryName);

        // Jeśli nie znaleziono kategorii, zwróć pustą listę
        if (categoryId == null) {
            return Collections.emptyList();
        }

        // Pobieramy książki dla znalezionej kategorii
        String sql = "SELECT * FROM public.\"Book\" WHERE \"Id_category\" = ?";
        return jdbcTemplate.query(sql, bookRowMapper, categoryId);
    }

    private List<Book> mapBooksByAuthor(List<Map<String, Object>> rows) {
        List<Book> books = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Book book = new Book();
            book.setId((Integer) row.get("Id_book"));
            book.setCategoryId((Integer) row.get("Id_category"));
            book.setTitle((String) row.get("Title"));
            book.setAuthor((String) row.get("Author"));
            books.add(book);
        }
        return books;
    }

    private Book mapBookByTitle(Map<String, Object> row) {
        Book book = new Book();
        book.setId((Integer) row.get("Id_book"));
        book.setCategoryId((Integer) row.get("Id_category"));
        book.setTitle((String) row.get("Title"));
        book.setAuthor((String) row.get("Author"));
        return book;
    }

}