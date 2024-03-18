package com.course.hibernate.reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/h/readers")
public class HReaderController {

    @Autowired
    private HReaderService hReaderService;

    @PostMapping("/add")
    public void addReader(@RequestParam String name, @RequestParam String surname) {
        hReaderService.addReader(name, surname);
    }

    @GetMapping("")
    public List<HReaderRents> getAllReaders() {
        return hReaderService.getAllReaders();
    }

    @GetMapping("/{readerId}")
    public HReader getReaderById(@PathVariable int readerId) {
        return hReaderService.getReaderById(readerId);
    }

    @DeleteMapping("/{readerId}")
    public void deleteReader(@PathVariable int readerId) {
        hReaderService.deleteReader(readerId);
    }

    @GetMapping("/surname/{surname}")
    public List<HReaderRents> getReadersBySurname(@PathVariable String surname) {
        return hReaderService.getReadersBySurname(surname);
    }
}
