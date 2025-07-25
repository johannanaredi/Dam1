package com.naredi.dam1.services;

import com.naredi.dam1.Entitys.AssetEntity;
import com.naredi.dam1.Entitys.NailpolishEntity;
import com.naredi.dam1.Repositorys.AssetRepository;
import com.naredi.dam1.Repositorys.NailpolishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class MegaService {

    @Autowired
    private NailpolishRepository nailpolishRepository;

    @Autowired
    private AssetRepository assetRepository;

    public List<String> listMegaFiles() {
        List<String> list = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder("C:\\Users\\johan\\AppData\\Local\\MEGAcmd\\megaclient.exe", "ls");
            Process process = pb.start();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                list.add("Error: mega-ls failed");
            }
        } catch (Exception e) {
            list.add("Exception: " + e.getMessage());
        }

        return list;
    }

    public String syncMegaFilesToDatabase() {
        List<String> megaFiles = listMegaFiles();
        int createdCount = 0;

        for (String filename : megaFiles) {
            if (!nailpolishRepository.existsByName(filename)) {
                NailpolishEntity nailpolish = new NailpolishEntity();
                nailpolish.setName(filename); // sätt bara filnamnet som name
                nailpolishRepository.save(nailpolish);

                AssetEntity asset = new AssetEntity();
                asset.setFilename(filename);
                asset.setNailpolish(nailpolish);

                assetRepository.save(asset);

                createdCount++;
            }
        }
        return createdCount + " new nailpolish items synced from Mega.";
    }

}
