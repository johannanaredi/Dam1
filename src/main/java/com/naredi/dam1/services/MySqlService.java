package com.naredi.dam1.services;

import com.naredi.dam1.DTO.AssetDto;
import com.naredi.dam1.Entitys.AssetEntity;
import com.naredi.dam1.Entitys.NailpolishEntity;
import com.naredi.dam1.Repositorys.AssetRepository;
import com.naredi.dam1.Repositorys.NailpolishRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MySqlService {

    @Autowired
    private NailpolishRepository nailpolishRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private MegaService megaService;

    public String syncAllFilesWithUrlsToDatabase() {

        System.out.println("Startar synkronisering från MEGA till databasen.");
        List<AssetDto> allFilesWithUrls = megaService.exportAllFilesAndGetLinks();
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
                asset.setNailpolish(nailpolish);
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

