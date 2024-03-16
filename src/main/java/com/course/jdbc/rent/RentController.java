package com.course.jdbc.rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rents")
public class RentController {

    @Autowired
    private RentService rentService;

    @GetMapping("")
    public List<RentDetail> getAllRents() {
        return rentService.getAllRents();
    }

    @GetMapping("/{rentId}")
    public RentDetail getRentById(@PathVariable("rentId") int rentId) {
        return rentService.getRentById(rentId);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRent(@RequestParam int readerId, @RequestParam int copyId) {
        try {
            rentService.addRent(readerId, copyId);
            return ResponseEntity.ok("Wypożyczenie zostało dodane.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateRent(@RequestParam int copyId) {
        try {
            rentService.updateRent(copyId);
            return ResponseEntity.ok("Wypożyczenie zostało zaktualizowane.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{rentId}")
    public void deleteRent(@PathVariable("rentId") int rentId) {
        rentService.deleteRent(rentId);
    }

    @GetMapping("/reader/{readerId}/rents")
    public List<Rent> getRentsForReader(@PathVariable int readerId) {
        return rentService.getRentsForReader(readerId);
    }
}