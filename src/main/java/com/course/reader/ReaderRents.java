package com.course.Reader;

public class ReaderRents {
    private int id;
    private String name;
    private String surname;
    private long totalBooks;
    private long notReturnedBooks;

    // Konstruktory, gettery i settery
    public ReaderRents() {}

    public ReaderRents(int id, String name, String surname, long totalBooks, long notReturnedBooks) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.totalBooks = totalBooks;
        this.notReturnedBooks = notReturnedBooks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(long totalBooks) {
        this.totalBooks = totalBooks;
    }

    public long getNotReturnedBooks() {
        return notReturnedBooks;
    }

    public void setNotReturnedBooks(long notReturnedBooks) {
        this.notReturnedBooks = notReturnedBooks;
    }
}