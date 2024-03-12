package com.course.Rent;

import java.util.List;

public interface RentRepository {
    void addRent(Rent rent);
    List<Rent> getAllRents();
    Rent getRentById(int id);
    void updateRent(Rent rent);
    void deleteRent(int id);
}