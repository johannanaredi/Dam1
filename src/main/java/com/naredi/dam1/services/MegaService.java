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

//denna funkar för en länk
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

}
