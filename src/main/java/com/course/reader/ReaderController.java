package com.course.Reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/readers")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @GetMapping("")
    public List<Reader> getAllReaders() {
        return readerService.getAllReaders();
    }

    @GetMapping("/{readerId}")
    public Reader getReaderById(@PathVariable("readerId") int readerId) {
        return readerService.getReaderById(readerId);
    }

    @PostMapping("")
    public void addReader(@RequestBody Reader reader) {
        readerService.addReader(reader);
    }

    @DeleteMapping("/{readerId}")
    public void deleteReader(@PathVariable("readerId") int readerId) {
        readerService.deleteReader(readerId);
    }

    @GetMapping("/surname/{surname}")
    public List<Reader> getReadersBySurname(@PathVariable String surname) {
        return readerService.getReadersBySurname(surname);
    }
}