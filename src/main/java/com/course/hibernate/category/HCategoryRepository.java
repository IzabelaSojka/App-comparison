package com.course.hibernate.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HCategoryRepository extends JpaRepository<HCategory, Integer> {
    HCategory findByName(String categoryName);
}
