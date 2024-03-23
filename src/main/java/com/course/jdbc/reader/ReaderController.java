package com.course.jdbc.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/readers")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @GetMapping("")
    public List<ReaderRents> getAllReaders() {
        return readerService.getAllReaders();
    }

    @GetMapping("/{readerId}")
    public Reader getReaderById(@PathVariable("readerId") int readerId) {
        return readerService.getReaderById(readerId);
    }

    @PostMapping("/add")
    public void addReader(@RequestParam String name, @RequestParam String surname, @RequestParam String phone) {
        readerService.addReader(name, surname, phone);
    }

    @DeleteMapping("/{surname}")
    public void deleteReader(@PathVariable("surname") String surname) {
        readerService.deleteReader(surname);
    }

    @GetMapping("/surname/{surname}")
    public List<ReaderInfo> getReadersBySurname(@PathVariable String surname) {
        return readerService.getReadersBySurname(surname);
    }
}

