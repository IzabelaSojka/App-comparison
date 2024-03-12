package com.course.Rent;

import java.sql.Date;


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

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateRent() {
        return dateRent;
    }

    public void setDateRent(Date dateRent) {
        this.dateRent = dateRent;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

    public int getCopyId() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId = copyId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }
}
