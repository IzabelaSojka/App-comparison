package com.course.hibernate.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Category\"", schema = "public")
public class HCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id_category\"", columnDefinition = "\"Id_category\"")
    private Integer id;
    @Column(name = "\"Name\"", columnDefinition = "\"Name\"")
    private String name;

    public HCategory() {}

    public HCategory(String name) {
        this.name = name;
    }

}