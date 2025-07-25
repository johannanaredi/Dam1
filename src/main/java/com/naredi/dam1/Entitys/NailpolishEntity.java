package com.naredi.dam1.Entitys;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "nailpolish")
public class NailpolishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 50)
    private String brand;

    @Column(length = 30)
    private String finish;

    @Column(length = 30)
    private String color;

    @Column(name = "size_ml", precision = 5, scale = 2)
    private Double sizeMl;

    @Column(length = 30)
    private String coverage;

    @Column(name = "created_at", insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "nailpolish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NailpolishFileEntity> files;

}
