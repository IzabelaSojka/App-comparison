package com.course.jdbc.reader;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReaderRents {
    private int id;
    private String name;
    private String surname;
    private long totalBooks;
    private long notReturnedBooks;
    public ReaderRents() {}

    public ReaderRents(int id, String name, String surname, long totalBooks, long notReturnedBooks) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.totalBooks = totalBooks;
        this.notReturnedBooks = notReturnedBooks;
    }
}