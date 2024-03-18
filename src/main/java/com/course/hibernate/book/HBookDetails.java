package com.course.hibernate.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HBookDetails {
    private String title;
    private String author;
    private String category;
    private int totalCopies;
    private int availableCopies;
}
