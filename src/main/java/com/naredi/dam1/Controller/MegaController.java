package com.naredi.dam1.Controller;


import com.naredi.dam1.Repositorys.AssetRepository;
import com.naredi.dam1.Repositorys.NailpolishRepository;
import com.naredi.dam1.services.MegaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mega")
public class MegaController {

    private final MegaService megaService;
    private final NailpolishRepository nailpolishRepository;
    private final AssetRepository nailpolishFileRepository;

    @Autowired
    public MegaController(MegaService megaService, NailpolishRepository nailpolishRepository, AssetRepository nailpolishFileRepository) {
        this.megaService = megaService;
        this.nailpolishRepository = nailpolishRepository;
        this.nailpolishFileRepository = nailpolishFileRepository;
    }
    @GetMapping("/files")
    public List<String> getMegaFiles() {
        return megaService.listMegaFiles();
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportFile(@RequestParam("file") String filename) {
        String link = megaService.exportMegaFile(filename);
        if (link != null) {
            return ResponseEntity.ok(link);
        } else {
            return ResponseEntity.status(500).body("Kunde inte exportera filen eller ingen länk hittades.");
        }
    }

/*
    @PostMapping("/sync-all-files")
    public ResponseEntity<String> syncMegaFiles() {
        String result = megaService.syncMegaFilesToDatabase();
        return ResponseEntity.ok(result);
    }

 */
}
