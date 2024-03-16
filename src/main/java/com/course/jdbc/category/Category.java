package com.course.jdbc.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private int id;
    private String name;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }
}