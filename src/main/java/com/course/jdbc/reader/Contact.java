package com.course.jdbc.reader;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {
    private Integer idReader;
    private String phone;

    public Contact() {
    }

    public Contact(Integer idReader, String phone) {
        this.idReader = idReader;
        this.phone = phone;
    }
}
