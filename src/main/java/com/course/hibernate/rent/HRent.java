package com.course.hibernate.rent;

import com.course.hibernate.reader.HReader;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "\"Rent\"", schema = "public")
public class HRent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id_rent\"", columnDefinition = "\"Id_rent\"")
    private int id;
    @Column(name = "\"Date_rent\"", columnDefinition = "\"Date_rent\"")
    private Date dateRent;
    @Column(name = "\"Date_return\"", columnDefinition = "\"Date_return\"")
    private Date dateReturn;
    @Column(name = "\"Id_copy\"", columnDefinition = "\"Id_copy\"")
    private int copyId;

    @ManyToOne
    @JoinColumn(name = "\"Id_reader\"")
    private HReader reader;
    public HRent() {}

    public HRent(Date dateRent, Date dateReturn, int copyId, HReader reader) {
        this.dateRent = dateRent;
        this.dateReturn = dateReturn;
        this.copyId = copyId;
        this.reader= reader;
    }
}