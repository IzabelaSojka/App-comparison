package com.course.Book;

import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BookRepository {
    void addBook(Book book);
    List<Book> getAllBooks();
    Book getBookById(Integer id);
    void updateBook(Book book);
    void deleteBook(Integer id);





}
