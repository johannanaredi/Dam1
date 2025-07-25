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

    @Column(name = "size_ml")
    private Double sizeMl;

    @Column(length = 30)
    private String coverage;

    @Column(name = "created_at", insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "nailpolish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssetEntity> files;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getSizeMl() {
        return sizeMl;
    }

    public void setSizeMl(Double sizeMl) {
        this.sizeMl = sizeMl;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<AssetEntity> getFiles() {
        return files;
    }

    public void setFiles(List<AssetEntity> files) {
        this.files = files;
    }
}
