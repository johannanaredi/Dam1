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

import com.naredi.dam1.Entitys.AssetEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mega")
public class LoginAdminController {

    private final MegaService megaService;
    private final NailpolishRepository nailpolishRepository;
    private final AssetRepository assetRepository;

    @Autowired
    public LoginAdminController(MegaService megaService, NailpolishRepository nailpolishRepository, AssetRepository assetRepository) {
        this.megaService = megaService;
        this.nailpolishRepository = nailpolishRepository;
        this.assetRepository = assetRepository;
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
        return megaService.exportAllFilesAndGetLinks();
    }

    @GetMapping("/export/missing")
    public ResponseEntity<String> exportMissing() {
        megaService.exportMissingLinks();
        return ResponseEntity.ok("De nya länkarna är exporterade!");
    }
}
