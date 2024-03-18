package com.course.hibernate.book;

import com.course.hibernate.category.HCategory;
import com.course.hibernate.category.HCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HBookService {
    @Autowired
    private HBookRepository bookRepository;
    @Autowired
    private HCategoryRepository categoryRepository;

    @Autowired
    public HBookService(HBookRepository bookRepository, HCategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void addBook(String title, String author, String categoryName) {
        HCategory category = categoryRepository.findByName(categoryName);
        if (category != null) {
            HBook book = new HBook();
            book.setTitle(title);
            book.setAuthor(author);
            book.setIdcategory(category.getId());
            bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("Category not found with name: " + categoryName);
        }
    }

    public List<HBookDetails> getAllBooks() {
        List<HBook> books = bookRepository.findAll();
        return books.stream()
                .map(this::mapToBookDetails)
                .collect(Collectors.toList());
    }

    public HBookDetails getBookById(Integer bookId) {
        HBook book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            return mapToBookDetails(book);
        }
        return null;
    }

    public List<HBookDetails> getBooksByAuthor(String author) {
        List<HBook> books = bookRepository.findBooksByAuthor(author);
        return books.stream()
                .map(this::mapToBookDetails)
                .collect(Collectors.toList());
    }

    public HBookDetails getBooksByTitle(String title) {
        HBook book = bookRepository.findBookByTitle(title);
        if (book != null) {
            return mapToBookDetails(book);
        }
        return null;
    }

    public List<HBookDetails> getBooksForCategory(String categoryName) {
        HCategory category = categoryRepository.findByName(categoryName);
        if (category != null) {
            List<HBook> books = bookRepository.findBooksByIdcategory(category.getId());
            List<HBookDetails> bookDetailsList = new ArrayList<>();
            for (HBook book : books) {
                HBookDetails bookDetails = mapToBookDetails(book);
                bookDetailsList.add(bookDetails);
            }
            return bookDetailsList;
        } else {
            throw new IllegalArgumentException("Category not found with name: " + categoryName);
        }
    }
    public HBook getBookByTitle(String bookTitle) {
        return bookRepository.findBookByTitle(bookTitle);
    }

    public HBookDetails mapToBookDetails(HBook book) {
        Optional<HCategory> optionalCategory = categoryRepository.findById(book.getIdcategory());
        String category = optionalCategory.map(HCategory::getName).orElse(null);
        HBookDetails details = new HBookDetails();
        details.setTitle(book.getTitle());
        details.setAuthor(book.getAuthor());
        details.setCategory(category);
        int totalCopies = book.getCopies().size();
        int availableCopies = (int) book.getCopies().stream().filter(copy -> "dostępny".equals(copy.getStatus())).count();
        details.setTotalCopies(totalCopies);
        details.setAvailableCopies(availableCopies);

        return details;
    }

}
