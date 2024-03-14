package com.course.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("jdbcBookController")
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("/Title/{Title}")
    public Book getBooksByTitle(@PathVariable String Title) {
        return bookService.getBooksByTitle(Title);
    }
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/category/{categoryName}/books")
    public List<Book> getBooksForCategory(@PathVariable String categoryName) {
        return bookService.getBooksForCategory(categoryName);
    }
}