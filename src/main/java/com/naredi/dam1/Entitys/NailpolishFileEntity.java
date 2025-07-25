package com.naredi.dam1.Entitys;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "nailpolish_files")
public class NailpolishFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String filename;

    @Column(name = "mega_url", length = 500)
    private String megaUrl;

    @Column(name = "uploaded_at", insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp uploadedAt;

    @ManyToOne
    @JoinColumn(name = "nailpolish_id", nullable = false)
    private NailpolishEntity nailpolish;

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
