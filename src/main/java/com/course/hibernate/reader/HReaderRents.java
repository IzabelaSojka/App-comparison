package com.course.hibernate.reader;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HReaderRents {
    private int id;
    private String name;
    private String surname;
    private long totalBooks;
    private long notReturnedBooks;


}