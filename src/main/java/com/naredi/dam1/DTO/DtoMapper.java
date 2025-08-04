package com.naredi.dam1.DTO;

import com.naredi.dam1.Entitys.AssetEntity;
import com.naredi.dam1.Entitys.NailpolishEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {

    public static AssetDto assetToDto(AssetEntity entity){
        AssetDto dto = new AssetDto();
        dto.setId(entity.getId());
        dto.setFilename(entity.getFilename());
        dto.setMegaUrl(entity.getMegaUrl());
        dto.setFileType(entity.getFileType());
        dto.setUploadedAt(entity.getUploadedAt());
        return dto;
    }

    public static NailpolishDto nailpolishToDto(NailpolishEntity entity){
        NailpolishDto dto = new NailpolishDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setBrand(entity.getBrand());
        dto.setFinish(entity.getFinish());
        dto.setColor(entity.getColor());
        dto.setSizeMl(entity.getSizeMl());

        List<AssetDto> assetDto = entity.getFiles()
                .stream()
                .map(DtoMapper:: assetToDto)
                .collect(Collectors.toList());

        dto.setFiles(assetDto);
        return dto;
    }

    public static SimpleNailpolishDto simpleNailpolishToDto(NailpolishEntity entity) {
        SimpleNailpolishDto dto = new SimpleNailpolishDto();
        dto.setName(entity.getName());
        dto.setBrand(entity.getBrand());
        dto.setFinish(entity.getFinish());
        dto.setColor(entity.getColor());
        dto.setSizeMl(entity.getSizeMl());
        dto.setCoverage(entity.getCoverage());
        return dto;
    }

}
