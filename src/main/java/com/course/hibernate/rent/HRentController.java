package com.course.hibernate.rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/h/rents")
public class HRentController {
    @Autowired
    private HRentService rentService;

    @PostMapping("/add")
    public ResponseEntity<String> addRent(@RequestParam int readerId, @RequestParam int copyId) {
        try {
            rentService.addRent(readerId, copyId);
            return ResponseEntity.ok("Wypożyczenie zostało dodane pomyślnie.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<HRent>> getAllRents() {
        List<HRent> rents = rentService.getAllRents();
        return ResponseEntity.ok(rents);
    }

    @GetMapping("/{rentId}")
    public ResponseEntity<HRent> getRentById(@PathVariable int rentId) {
        HRent rent = rentService.getRentById(rentId);
        if (rent != null) {
            return ResponseEntity.ok(rent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateRent(@RequestParam int copyId) {
        try {
            rentService.updateRent(copyId);
            return ResponseEntity.ok("Wypożyczenie zostało zaktualizowane pomyślnie.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{rentId}")
    public ResponseEntity<String> deleteRent(@PathVariable int rentId) {
        rentService.deleteRent(rentId);
        return ResponseEntity.ok("Wypożyczenie zostało usunięte pomyślnie.");
    }

    @GetMapping("/reader/{readerId}")
    public ResponseEntity<List<HRent>> getRentsForReader(@PathVariable int readerId) {
        List<HRent> rents = rentService.getRentsForReader(readerId);
        return ResponseEntity.ok(rents);
    }
}
