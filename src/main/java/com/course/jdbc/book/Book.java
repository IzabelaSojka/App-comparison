package com.course.jdbc.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private Integer Id_book;
    private Integer Id_category;
    private String Title;
    private String Author;

    public Book() {}

    public Book(Integer categoryId, String Title, String author) {
        this.Id_category = categoryId;
        this.Title = Title;
        this.Author = author;
    }

}
