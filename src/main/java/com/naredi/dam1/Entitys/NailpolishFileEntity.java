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



}
