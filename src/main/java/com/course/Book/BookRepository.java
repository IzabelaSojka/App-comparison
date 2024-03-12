package com.course.Book;

import java.util.List;

public interface BookRepository {
    void addBook(Book book);
    List<Book> getAllBooks();
    Book getBookById(int id);
    void updateBook(Book book);
    void deleteBook(int id);
}
