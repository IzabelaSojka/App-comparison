package com.course.jdbc.rent;

import com.course.jdbc.reader.Reader;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class RentDetail {
    private int id;
    private Date dateRent;
    private Date dateReturn;
    private int copyId;
    private Reader reader;
}
