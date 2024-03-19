package com.course.hibernate.copy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/h/copies")
public class HCopyController {
    @Autowired
    private final HCopyService copyService;

    @Autowired
    public HCopyController(HCopyService copyService) {
        this.copyService = copyService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCopy(@RequestParam String bookTitle) {
        try {
            copyService.addCopy(bookTitle);
            return ResponseEntity.ok("Copy added successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("")
    public List<HCopy> getAllCopies() {
        return copyService.getAllCopies();
    }

    @GetMapping("/{copyId}")
    public ResponseEntity<HCopy> getCopyById(@PathVariable int copyId) {
        HCopy copy = copyService.getCopyById(copyId);
        if (copy != null) {
            return ResponseEntity.ok(copy);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{copyId}")
    public ResponseEntity<String> deleteCopy(@PathVariable int copyId) {
        try {
            copyService.deleteCopy(copyId);
            return ResponseEntity.ok("Copy deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/borrowed")
    public List<HCopy> getBorrowedCopies() {
        return copyService.getBorrowedCopies();
    }
}
