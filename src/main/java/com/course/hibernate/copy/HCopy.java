package com.course.hibernate.copy;

import com.course.hibernate.book.HBook;
import com.course.hibernate.reader.HReader;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Copy\"", schema = "public")
public class HCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id_copy\"", columnDefinition = "\"Id_copy\"")
    private int id;
    @Column(name = "\"Status\"", columnDefinition = "\"Status\"")
    private String status;

    @ManyToOne
    @JoinColumn(name = "\"Id_book\"")
    private HBook book;

    public HCopy() {}

    public HCopy(HBook book, String status) {
        this.book = book;
        this.status = status;
    }
}
