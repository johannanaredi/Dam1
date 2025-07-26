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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MegaService {

    @Autowired
    private NailpolishRepository nailpolishRepository;

    @Autowired
    private AssetRepository assetRepository;

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

    public List<Map<String, String>> exportAllFilesAndGetLinks() {
        List<Map<String, String>> exportedFiles = new ArrayList<>();

        try {
            // 1. Lista alla filer i root
            ProcessBuilder lsBuilder = new ProcessBuilder(
                    "C:\\Users\\johan\\AppData\\Local\\MEGAcmd\\MEGAclient.exe", "ls"
            );
            Process lsProcess = lsBuilder.start();
            BufferedReader lsReader = new BufferedReader(new InputStreamReader(lsProcess.getInputStream()));

            List<String> filenames = new ArrayList<>();
            String line;
            while ((line = lsReader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String cleaned = line.trim();
                    String[] parts = cleaned.split("\\s+");
                    String filename = parts[parts.length - 1];
                    filenames.add(filename);
                }
            }

            lsProcess.waitFor();

            for (String filename : filenames) {
                ProcessBuilder exportBuilder = new ProcessBuilder(
                        "C:\\Users\\johan\\AppData\\Local\\MEGAcmd\\MEGAclient.exe",
                        "export", "/" + filename
                );
                Process exportProcess = exportBuilder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(exportProcess.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    if (line.contains("https://mega.nz/")) {
                        Map<String, String> fileMap = new HashMap<>();
                        fileMap.put("filename", filename);
                        fileMap.put("url", line.substring(line.indexOf("https://")).trim());
                        exportedFiles.add(fileMap);
                        break;
                    }
                }
                exportProcess.waitFor();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return exportedFiles;
    }

    public void exportMissingLinks() {
        try {
            // 1. Lista alla filer i MEGA
            ProcessBuilder lsBuilder = new ProcessBuilder(
                    "C:\\Users\\johan\\AppData\\Local\\MEGAcmd\\MEGAclient.exe", "ls"
            );
            Process lsProcess = lsBuilder.start();
            BufferedReader lsReader = new BufferedReader(new InputStreamReader(lsProcess.getInputStream()));

            String line;
            List<String> filenames = new ArrayList<>();
            while ((line = lsReader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.trim().split("\\s+");
                    String filename = parts[parts.length - 1];
                    filenames.add(filename);
                }
            }
            lsProcess.waitFor();

            // 2. Gå igenom varje fil
            for (String filename : filenames) {
                System.out.println("Kontrollerar länk för: " + filename);

                // Försök få länk utan att skapa ny
                ProcessBuilder exportCheck = new ProcessBuilder(
                        "C:\\Users\\johan\\AppData\\Local\\MEGAcmd\\MEGAclient.exe",
                        "export", "/" + filename
                );
                Process checkProcess = exportCheck.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(checkProcess.getInputStream()));

                boolean linkExists = false;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("https://mega.nz/")) {
                        linkExists = true;
                        break;
                    }
                }
                checkProcess.waitFor();

                if (!linkExists) {
                    System.out.println(" Ingen länk hittad, exporterar: " + filename);
                    ProcessBuilder exportAdd = new ProcessBuilder(
                            "C:\\Users\\johan\\AppData\\Local\\MEGAcmd\\MEGAclient.exe",
                            "export", "-a", "/" + filename
                    );
                    Process exportProcess = exportAdd.start();
                    exportProcess.waitFor();
                } else {
                    System.out.println(" Länk finns redan för: " + filename);
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
