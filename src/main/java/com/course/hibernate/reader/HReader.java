package com.course.hibernate.reader;

import com.course.hibernate.rent.HRent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"Reader\"", schema = "public")
public class HReader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id_reader\"", columnDefinition = "\"Id_reader\"")
    private int id;

    @Column(name = "\"Name\"", columnDefinition = "\"Name\"")
    private String name;

    @Column(name = "\"Surname\"", columnDefinition = "\"Surname\"")
    private String surname;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<HRent> rents;

    @OneToOne(mappedBy = "reader", cascade = CascadeType.ALL)
    @JsonIgnore
    private HContact contact;
    public HReader() {}

    public HReader(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
