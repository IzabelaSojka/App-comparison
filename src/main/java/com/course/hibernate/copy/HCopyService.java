package com.course.hibernate.copy;

import com.course.hibernate.book.HBook;
import com.course.hibernate.book.HBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HCopyService {
    @Autowired
    private final HCopyRepository copyRepository;
    @Autowired
    private final HBookService bookService;

    @Autowired
    public HCopyService(HCopyRepository copyRepository, HBookService bookService) {
        this.copyRepository = copyRepository;
        this.bookService = bookService;
    }

    @Transactional
    public void addCopy(String bookTitle) {
        HBook book = bookService.getBookByTitle(bookTitle);
        if (book != null) {
            HCopy copy = new HCopy(book, "dostępny");
            copyRepository.save(copy);
        } else {
            throw new IllegalArgumentException("Book not found with title: " + bookTitle);
        }
    }

    public List<HCopy> getAllCopies() {
        return copyRepository.findAll();
    }

    public HCopy getCopyById(int copyId) {
        return copyRepository.findById(copyId).orElse(null);
    }

    @Transactional
    public void deleteCopy(int copyId) {
        HCopy copy = copyRepository.findById(copyId).orElse(null);
        if (copy != null) {
            copyRepository.delete(copy);
        } else {
            throw new IllegalArgumentException("Copy not found with ID: " + copyId);
        }
    }

    public List<HCopy> getBorrowedCopies() {
        return copyRepository.findByStatus("wypożyczony");
    }
}
