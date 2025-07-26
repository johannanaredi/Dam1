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


/*
    public String syncMegaFilesToDatabase() {
        List<String> megaFiles = listMegaFiles();
        int createdCount = 0;

        for (String filename : megaFiles) {
            if (!nailpolishRepository.existsByName(filename)) {
                NailpolishEntity nailpolish = new NailpolishEntity();
                nailpolish.setName(filename);
                nailpolishRepository.save(nailpolish);

                AssetEntity asset = new AssetEntity();
                asset.setFilename(filename);
                asset.setNailpolish(nailpolish);

                String megaUrl = getMegaFileUrl(filename);
                asset.setMegaUrl(megaUrl);
                assetRepository.save(asset);

                createdCount++;
            }
        }
        return createdCount + " new nailpolish items synced from Mega.";
    }
*/

/*
    public String getMegaFileUrl(String filename) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "C:\\Users\\johan\\AppData\\Local\\MEGAcmd\\megaclient.exe",
                    "export",
                    "-a",
                    "/" + filename

            );

            Process process = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String megaUrl = null;
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("https://")) {
                    megaUrl = line;
                    break;
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("megaclient export failed with exit code: " + exitCode);
                return null;
            }

            return megaUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
*/