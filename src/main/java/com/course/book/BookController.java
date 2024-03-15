package com.course.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("jdbcBookController")
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public void addBook(@RequestBody BookRequest bookRequest) {
        bookService.addBook(bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getCategoryName());
    }

    @GetMapping
    public List<BookDetails> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/author/{author}")
    public List<BookDetails> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("/Title/{Title}")
    public BookDetails getBooksByTitle(@PathVariable String Title) {
        return bookService.getBooksByTitle(Title);
    }
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/category/{categoryName}/books")
    public List<BookDetails> getBooksForCategory(@PathVariable String categoryName) {
        return bookService.getBooksForCategory(categoryName);
    }
}