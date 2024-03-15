package com.course.Reader;

public class Reader {
    private int id;
    private String name;
    private String surname;

    public Reader() {}

    public Reader(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    // Getters and setters
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
}

