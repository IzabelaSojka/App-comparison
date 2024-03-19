package com.course.hibernate.copy;

import com.course.hibernate.category.HCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HCopyRepository extends JpaRepository<HCopy, Integer> {
    List<HCopy> findByStatus(String wypożyczony);
}
