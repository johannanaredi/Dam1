package com.naredi.dam1.Controller;

import com.naredi.dam1.DTO.AssetDto;
import com.naredi.dam1.Repositorys.AssetRepository;
import com.naredi.dam1.Repositorys.NailpolishRepository;
import com.naredi.dam1.services.MegaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mega")
public class LoginAdminController {

    private final MegaService megaService;
    private final NailpolishRepository nailpolishRepository;
    private final AssetRepository nailpolishFileRepository;

    @Autowired
    public LoginAdminController(MegaService megaService, NailpolishRepository nailpolishRepository, AssetRepository nailpolishFileRepository) {
        this.megaService = megaService;
        this.nailpolishRepository = nailpolishRepository;
        this.nailpolishFileRepository = nailpolishFileRepository;
    }

    @GetMapping("/admin")
    public String adminHello() {
        return "Hej admin!";
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
