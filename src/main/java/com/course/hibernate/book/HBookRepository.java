package com.course.hibernate.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HBookRepository extends JpaRepository <HBook, Integer> {
    HBook findBookByTitle(String title);
    List<HBook> findBooksByAuthor(String author);
    List<HBook> findBooksByIdcategory(Integer idcategory);
}
