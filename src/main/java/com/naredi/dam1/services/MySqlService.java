package com.naredi.dam1.services;

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
        List<Map<String, String>> allFilesWithUrls = megaService.exportAllFilesAndGetLinks();
        int createdCount = 0;

        for (Map<String, String> file : allFilesWithUrls) {
            String filename = file.get("filename");
            String url = file.get("url");

            if (!nailpolishRepository.existsByName(filename)) {
                NailpolishEntity nailpolish = new NailpolishEntity();
                nailpolish.setName(filename);
                nailpolishRepository.save(nailpolish);

                AssetEntity asset = new AssetEntity();
                asset.setFilename(filename);
                asset.setMegaUrl(url);
                asset.setNailpolish(nailpolish);
                assetRepository.save(asset);

                createdCount++;
            }
        }

        return createdCount + " new nailpolish items synced from Mega.";
    }

    @PostConstruct
    public void init() {
        System.out.println("Kör synk vid start...");
        syncAllFilesWithUrlsToDatabase();
    }

}

