package com.naredi.dam1.DTO;



import java.sql.Timestamp;
import java.util.List;

public class NailpolishDTO {
    private int id;
    private String name;
    private String brand;
    private String finish;
    private String color;
    private Double sizeMl;
    private String coverage;
    private Timestamp createdAt;
    private List<AssetDto> files;

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

    public List<AssetDto> getFiles() {
        return files;
    }

    public void setFiles(List<AssetDto> files) {
        this.files = files;
    }
}

