package com.course.hibernate.rent;

import com.course.hibernate.category.HCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HRentRepository extends JpaRepository<HRent, Integer> {

    HRent findFirstByCopyIdAndDateReturnIsNull(int copyId);

    List<HRent> findByReaderId(int readerId);
}
