/*
package com.naredi.dam1.Controller;

import com.naredi.dam1.DTO.AssetDto;

import com.naredi.dam1.Entitys.AssetEntity;
import com.naredi.dam1.Repositorys.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mega")
public class MySqlController {

    private final AssetRepository assetRepository;

    public MySqlController(AssetRepository assetRepository) {
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

}

*/