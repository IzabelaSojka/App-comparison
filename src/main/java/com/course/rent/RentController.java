package com.course.Rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rents")
public class RentController {

    @Autowired
    private RentService rentService;

    @GetMapping("")
    public List<Rent> getAllRents() {
        return rentService.getAllRents();
    }

    @GetMapping("/{rentId}")
    public Rent getRentById(@PathVariable("rentId") int rentId) {
        return rentService.getRentById(rentId);
    }

    @PostMapping("")
    public void addRent(@RequestBody Rent rent) {
        rentService.addRent(rent);
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