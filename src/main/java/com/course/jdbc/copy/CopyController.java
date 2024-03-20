package com.course.jdbc.copy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/copies")
public class CopyController {
    @Autowired
    private CopyService copyService;

    @GetMapping("")
    public List<CopyDetails> getAllCopies() {
        return copyService.getAllCopies();
    }

    @GetMapping("/{copyId}")
    public Map<String, Object> getCopyById(@PathVariable("copyId") int copyId) {
        return copyService.getCopyById(copyId);
    }

    @PostMapping("/add")
    public void addCopy(@RequestParam String bookTitle) {
        copyService.addCopy(bookTitle);
    }

    @DeleteMapping("/{copyId}")
    public void deleteCopy(@PathVariable int copyId) {
        copyService.deleteCopy(copyId);
    }

    @GetMapping("/borrowed-copies")
    public List<CopyDetails> getBorrowedCopies() {
        return copyService.getBorrowedCopies();
    }
}
