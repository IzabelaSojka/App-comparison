package com.course.jdbc.reader;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reader {
    private int id;
    private String name;
    private String surname;

    public Reader() {}

    public Reader(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}

