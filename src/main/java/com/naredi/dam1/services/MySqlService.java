package com.naredi.dam1.services;

import com.naredi.dam1.DTO.AssetDto;
import com.naredi.dam1.DTO.DtoMapper;
import com.naredi.dam1.DTO.SimpleNailpolishDto;
import com.naredi.dam1.Entitys.AssetEntity;
import com.naredi.dam1.Entitys.NailpolishEntity;
import com.naredi.dam1.Repositorys.AssetRepository;
import com.naredi.dam1.Repositorys.NailpolishRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MySqlService {

    @Autowired
    private NailpolishRepository nailpolishRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private MegaService megaService;

    public SimpleNailpolishDto updateNailpolishByName(String name, SimpleNailpolishDto dto) {
        Optional<NailpolishEntity> optional = nailpolishRepository.findByName(name);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Nagellack med namn " + name + " finns inte");
        }

        NailpolishEntity existing = optional.get();

        existing.setBrand(dto.getBrand());
        existing.setFinish(dto.getFinish());
        existing.setColor(dto.getColor());
        existing.setSizeMl(dto.getSizeMl());
        existing.setCoverage(dto.getCoverage());

        NailpolishEntity saved = nailpolishRepository.save(existing);
        return DtoMapper.simpleNailpolishToDto(saved);
    }

    public List<SimpleNailpolishDto> listSimpleNailpolish() {
        return nailpolishRepository.findAll()
                .stream()
                .map(DtoMapper::simpleNailpolishToDto)
                .collect(Collectors.toList());
    }

    public String syncAllFilesWithUrlsToDatabase() {

        System.out.println("Startar synkronisering från MEGA till databasen.");
        List<AssetDto> allFilesWithUrls = megaService.exportAllAssetsAndGetLinks();
        int createdCount = 0;

        for (AssetDto file : allFilesWithUrls) {
            String filename = file.getFilename();
            String url = file.getMegaUrl();

            System.out.println("Kontrollerar fil: " + filename);

            if (!nailpolishRepository.existsByName(filename)) {
                System.out.println("Filen finns inte i databasen. Skapar ny post: " + filename);

                NailpolishEntity nailpolish = new NailpolishEntity();
                nailpolish.setName(filename);
                nailpolishRepository.save(nailpolish);

                AssetEntity asset = new AssetEntity();
                asset.setFilename(filename);
                asset.setMegaUrl(url);
               // asset.setNailpolish(nailpolish);
                assetRepository.save(asset);

                System.out.println("Ny post skapad för: " + filename);
                createdCount++;
            }
            else {
                System.out.println("Filen finns redan i databasen: " + filename);
            }
        }
        return createdCount + " nytt nagellack är synkat från mega.";
    }

    @PostConstruct
    public void syncMySqlDataStartup() {
        System.out.println("Synkar MySQL data med mega vid uppstart av applikationen...");
        syncAllFilesWithUrlsToDatabase();
    }
}

