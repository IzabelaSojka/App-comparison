package com.course.hibernate.book;


import com.course.hibernate.copy.HCopy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"Book\"", schema = "public")
public class HBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id_book\"", columnDefinition = "\"Id_book\"")
    private Integer Id_book;
    @Column(name = "\"Id_category\"", columnDefinition = "\"Id_category\"")
    private Integer idcategory;
    @Column(name = "\"Title\"", columnDefinition = "\"Title\"")
    private String title;
    @Column(name = "\"Author\"", columnDefinition = "\"Author\"")
    private String author;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<HCopy> copies;

    public HBook() {}

    public HBook(Integer categoryId, String Title, String author) {
        this.idcategory = categoryId;
        this.title = Title;
        this.author = author;
    }

}