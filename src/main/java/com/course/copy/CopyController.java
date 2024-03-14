package com.course.Copy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/copies")
public class CopyController {
    @Autowired
    private CopyService copyService;

    @GetMapping("")
    public List<Copy> getAllCopies() {
        return copyService.getAllCopies();
    }

    @GetMapping("/{copyId}")
    public Copy getCopyById(@PathVariable("copyId") int copyId) {
        return copyService.getCopyById(copyId);
    }

    @PostMapping("")
    public void addCopy(@RequestBody Copy copy) {
        copyService.addCopy(copy);
    }

    @DeleteMapping("/{copyId}")
    public void deleteCopy(@PathVariable("copyId") int copyId) {
        copyService.deleteCopy(copyId);
    }

    @GetMapping("/borrowed-copies")
    public List<CopyDetails> getBorrowedCopies() {
        return copyService.getBorrowedCopies();
    }
}
