package com.course.jdbc.book;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookDetails {
    private String title;
    private String author;
    private String category;
    private int totalCopies;
    private int availableCopies;
}