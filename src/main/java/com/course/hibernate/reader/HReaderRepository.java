package com.course.hibernate.reader;

import com.course.hibernate.category.HCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HReaderRepository extends JpaRepository<HReader, Integer> {
    List<HReader> findBySurname(String surname);
}
