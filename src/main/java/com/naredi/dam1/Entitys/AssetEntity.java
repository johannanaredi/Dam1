package com.naredi.dam1.Entitys;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "nailpolish_assets")
public class AssetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String filename;

    @Column(name = "mega_url", length = 500)
    private String megaUrl;

    @Column(name = "file_type", length = 50)
    private String fileType;

    @Column(name = "uploaded_at", insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp uploadedAt;

    @ManyToOne
    @JoinColumn(name = "nailpolish_id")
    private NailpolishEntity nailpolish;

    public AssetEntity() {
    }

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

    public NailpolishEntity getNailpolish() {
        return nailpolish;
    }

    public void setNailpolish(NailpolishEntity nailpolish) {
        this.nailpolish = nailpolish;
    }
}
