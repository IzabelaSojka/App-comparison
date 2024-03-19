package com.course.hibernate.reader;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"contact\"", schema = "public")
public class HContact {
    @Id
    @Column(name = "\"Id_reader\"")
    private Integer id;

    @Column(name = "\"Phone\"")
    private String phone;

    @OneToOne
    @MapsId
    @JoinColumn(name = "\"Id_reader\"")
    private HReader reader;
}
