package com.course.jdbc.rent;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Rent {
    private int id;
    private Date dateRent;
    private Date dateReturn;
    private int copyId;
    private int readerId;

    public Rent() {}

    public Rent(Date dateRent, Date dateReturn, int copyId, int readerId) {
        this.dateRent = dateRent;
        this.dateReturn = dateReturn;
        this.copyId = copyId;
        this.readerId = readerId;
    }
}
