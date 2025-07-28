package com.naredi.dam1.DTO;

import java.sql.Timestamp;

public class AssetDto {
    private int id;
    private String filename;
    private String megaUrl;
    private String fileType;
    private Timestamp uploadedAt;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMegaUrl() {
        return megaUrl;
    }

    public void setMegaUrl(String megaUrl) {
        this.megaUrl = megaUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Timestamp getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Timestamp uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
