package com.course.hibernate.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/h/books")
public class HBookController {
    @Autowired
    private HBookService bookService;

    @Autowired
    public HBookController(HBookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestParam String title, @RequestParam String author, @RequestParam String categoryName) {
        try {
            bookService.addBook(title, author, categoryName);
            return ResponseEntity.ok("Book added successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public List<HBookDetails> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<HBookDetails> getBookById(@PathVariable Integer bookId) {
        HBookDetails book = bookService.getBookById(bookId);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @GetMapping("/author/{author}")
    public List<HBookDetails> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<HBookDetails> getBookByTitle(@PathVariable String title) {
        HBookDetails bookDetails = bookService.getBooksByTitle(title);
        return bookDetails != null ? ResponseEntity.ok(bookDetails) : ResponseEntity.notFound().build();
    }

    @GetMapping("/category/{categoryName}")
    public List<HBookDetails> getBooksForCategory(@PathVariable String categoryName) {
        return bookService.getBooksForCategory(categoryName);
    }
}
