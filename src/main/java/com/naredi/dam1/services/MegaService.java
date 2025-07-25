package com.naredi.dam1.services;

import com.naredi.dam1.Entitys.AssetEntity;
import com.naredi.dam1.Entitys.NailpolishEntity;
import com.naredi.dam1.Repositorys.AssetRepository;
import com.naredi.dam1.Repositorys.NailpolishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class MegaService {

    @Autowired
    private NailpolishRepository nailpolishRepository;

    @Autowired
    private AssetRepository assetRepository;
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
    public List<String> listMegaFiles() {
        List<String> list = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder("C:\\Users\\johan\\AppData\\Local\\MEGAcmd\\MEGAclient.exe", "ls");
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

    public String exportMegaFile(String filename) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "C:\\Users\\johan\\AppData\\Local\\MEGAcmd\\MEGAclient.exe",
                    "export",
                    "/" + filename
            );
            Process process = pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("https://mega.nz/")) {
                    return line.substring(line.indexOf("https://")).trim();
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("exportMegaFile failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
