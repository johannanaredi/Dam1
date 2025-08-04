package com.naredi.dam1.Controller;

import com.naredi.dam1.DTO.AssetDto;
import com.naredi.dam1.Repositorys.AssetRepository;
import com.naredi.dam1.Repositorys.NailpolishRepository;
import com.naredi.dam1.services.MegaService;
import com.naredi.dam1.services.MySqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.naredi.dam1.Entitys.AssetEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mega")
public class LoginAdminController {

    private final MegaService megaService;
    private final NailpolishRepository nailpolishRepository;
    private final AssetRepository assetRepository;
    private final MySqlService mySqlService;

    @Autowired
    public LoginAdminController(MegaService megaService, NailpolishRepository nailpolishRepository, AssetRepository assetRepository, MySqlService mySqlService) {
        this.megaService = megaService;
        this.nailpolishRepository = nailpolishRepository;
        this.assetRepository = assetRepository;
        this.mySqlService = mySqlService;
    }

    @GetMapping("/assets")
    public List<AssetDto> getAllAssets() {
        List<AssetEntity> entities = assetRepository.findAll();

        return entities.stream()
                .map(entity -> {
                    AssetDto dto = new AssetDto();
                    dto.setId(entity.getId());
                    dto.setFilename(entity.getFilename());
                    dto.setMegaUrl(entity.getMegaUrl());
                    dto.setFileType(entity.getFileType());
                    dto.setUploadedAt(entity.getUploadedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/admin")
    public String adminHello() {
        return "Hej admin!";
    }

    @GetMapping("/export/all")
    public List<AssetDto> exportAllAndReturnLinks() {
        return megaService.exportAllAssetsAndGetLinks();
    }

    @GetMapping("/export/missing")
    public ResponseEntity<String> exportMissing() {
        megaService.exportMissingLinks();
        return ResponseEntity.ok("De nya länkarna är exporterade!");
    }

    @DeleteMapping("/assets/{id}")
    public ResponseEntity<String> deleteAssetById(@PathVariable int id) {
        boolean success = megaService.deleteAssetById(id);
        if (success) {
            return ResponseEntity.ok("Tillgången och metadatan togs bort (id: " + id + ").");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Kunde inte hitta eller ta bort tillgången med id: " + id);
        }
    }
}
