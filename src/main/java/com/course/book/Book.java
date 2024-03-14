package com.course.Book;

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

    // Getters and setters
    public Integer getId() {
        return Id_book;
    }

    public void setId(Integer id) {
        this.Id_book = id;
    }

    public Integer getCategoryId() {
        return Id_category;
    }

    public void setCategoryId(Integer categoryId) {
        this.Id_category = categoryId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }
}
