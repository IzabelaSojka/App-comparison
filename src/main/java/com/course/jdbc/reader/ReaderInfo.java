package com.course.jdbc.reader;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReaderInfo {
    private int id;
    private String name;
    private String surname;
    private String phone;

    public ReaderInfo() {}

    public ReaderInfo(int id, String name, String surname, String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }
}