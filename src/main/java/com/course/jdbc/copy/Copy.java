package com.course.jdbc.copy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Copy {
    private int id;
    private int bookId;
    private String status;
    public Copy() {}
    public Copy(int bookId, String status) {
        this.bookId = bookId;
        this.status = status;
    }
}