package com.naredi.dam1.Controller;
import com.naredi.dam1.DTO.AssetDto;
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

    @GetMapping("/files")
    public List<String> getMegaFiles() {
        return megaService.listMegaFiles();
    }

    @GetMapping("/export/all")
    public List<AssetDto> exportAllAndReturnLinks() {
        return megaService.exportAllFilesAndGetLinks();
    }

    @GetMapping("/export/missing")
    public ResponseEntity<String> exportMissing() {
        megaService.exportMissingLinks();
        return ResponseEntity.ok("De nya länkarna är exporterade!");
    }


}
