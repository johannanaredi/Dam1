package com.naredi.dam1.Controller;


import com.naredi.dam1.Repositorys.AssetRepository;
import com.naredi.dam1.Repositorys.NailpolishRepository;
import com.naredi.dam1.services.MegaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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




//funkar att hämta en länk
    @GetMapping("/export")
    public ResponseEntity<String> exportFile(@RequestParam("file") String filename) {
        String link = megaService.exportMegaFile(filename);
        if (link != null) {
            return ResponseEntity.ok(link);
        } else {
            return ResponseEntity.status(500).body("Kunde inte exportera filen eller ingen länk hittades.");
        }
    }

    //funkar att hämta och exportera alla länkar
    @GetMapping("/export-all")
    public List<Map<String, String>> exportAllAndReturnLinks() {
        return megaService.exportAllFilesAndGetLinks();
    }

    @GetMapping("/files")
    public List<String> getMegaFiles() {
        return megaService.listMegaFiles();
    }

/*
    @PostMapping("/sync-all-files")
    public ResponseEntity<String> syncMegaFiles() {
        String result = megaService.syncMegaFilesToDatabase();
        return ResponseEntity.ok(result);
    }

 */
}
