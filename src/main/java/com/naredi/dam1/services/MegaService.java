package com.naredi.dam1.services;

import com.naredi.dam1.DTO.AssetDto;
import com.naredi.dam1.DTO.DTOMapper;
import com.naredi.dam1.Entitys.AssetEntity;
import com.naredi.dam1.Repositorys.AssetRepository;
import com.naredi.dam1.Repositorys.NailpolishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MegaService {

    @Autowired
    private NailpolishRepository nailpolishRepository;

    @Autowired
    private AssetRepository assetRepository;

    public List<String> listMegaFiles() {
        List<String> list = new ArrayList<>();
        try {
            System.out.println("Kör: ls i mega cmd");

            ProcessBuilder pb = new ProcessBuilder("C:\\Users\\johan\\AppData\\Local\\MEGAcmd\\MEGAclient.exe", "ls");
            Process process = pb.start();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("Hittade filen");
                list.add(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Exitkod från ls: " + exitCode);

            if (exitCode != 0) {
                list.add("Error: mega-ls failed");
                System.out.println("ls i mega funkade inte");
            }
        } catch (Exception e) {
            list.add("Exception: " + e.getMessage());
        }
        return list;
    }

    public List<AssetDto> exportAllFilesAndGetLinks() {
        List<AssetDto> exportedFiles = new ArrayList<>();

        try {
            System.out.println("Kör li i mega för att hämta filnamn...");
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
                    System.out.println("Fil hittad: " + filename);
                }
            }

            lsProcess.waitFor();
            System.out.println("Antal filer: " + filenames.size());

            for (String filename : filenames) {
                System.out.println("Försöker exportera: " + filename);
                ProcessBuilder exportBuilder = new ProcessBuilder(
                        "C:\\Users\\johan\\AppData\\Local\\MEGAcmd\\MEGAclient.exe",
                        "export", "/" + filename
                );

                Process exportProcess = exportBuilder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(exportProcess.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    if (line.contains("https://mega.nz/")) {
                        String megaUrl = line.substring(line.indexOf("https://")).trim();

                        Optional<AssetEntity> optionalEntity = assetRepository.findByFilename(filename);
                        if (optionalEntity.isPresent()) {
                            AssetDto dto = DTOMapper.assetToDto(optionalEntity.get());
                            exportedFiles.add(dto);
                        } else {
                            AssetDto dto = new AssetDto();
                            dto.setFilename(filename);
                            dto.setMegaUrl(megaUrl);
                            exportedFiles.add(dto);
                        }

                        System.out.println("Länk hittad för " + filename);
                        break;
                    }
                }
                exportProcess.waitFor();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Fel vid exportering: " + e.getMessage());
        }

        return exportedFiles;
    }
    @Transactional
    public List<AssetDto> exportMissingLinks() {
        List<AssetDto> result = new ArrayList<>();
        try {
            System.out.println("Kör: mega ls för att kontrollera alla filer...");
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
                    System.out.println("Fil hittad: " + filename);
                }
            }
            lsProcess.waitFor();
            System.out.println("Totalt antal filer att kontrollera: " + filenames.size());

            for (String filename : filenames) {
                System.out.println("Kontrollerar om url-länk finns för: " + filename);
                ProcessBuilder exportCheck = new ProcessBuilder(
                        "C:\\Users\\johan\\AppData\\Local\\MEGAcmd\\MEGAclient.exe",
                        "export", "/" + filename
                );
                Process checkProcess = exportCheck.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(checkProcess.getInputStream()));

                boolean linkExists = false;
                String megaUrl = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Kontrollerar utdata ");
                    if (line.contains("https://mega.nz/")) {
                        linkExists = true;
                        megaUrl = line.substring(line.indexOf("https://")).trim();
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

                    BufferedReader exportReader = new BufferedReader(new InputStreamReader(exportProcess.getInputStream()));
                    while ((line = exportReader.readLine()) != null) {
                        if (line.contains("https://mega.nz/")) {
                            megaUrl = line.substring(line.indexOf("https://")).trim();
                            break;
                        }
                    }

                    exportProcess.waitFor();
                    System.out.println("Länk skapad för: " + filename);
                } else {
                    System.out.println("Länk finns redan för: " + filename);
                }

                if (megaUrl != null) {
                    Optional<AssetEntity> optionalEntity = assetRepository.findByFilename(filename);
                    AssetDto dto;

                    if (optionalEntity.isPresent()) {
                        AssetEntity entity = optionalEntity.get();

                        if (!megaUrl.equals(entity.getMegaUrl())) {
                            entity.setMegaUrl(megaUrl); // <-- Uppdatera länk
                            assetRepository.save(entity);
                            System.out.println("URL uppdaterad i databasen för: " + filename);
                        }

                        dto = DTOMapper.assetToDto(entity);
                    } else {
                        AssetEntity newEntity = new AssetEntity();
                        newEntity.setFilename(filename);
                        newEntity.setMegaUrl(megaUrl);
                        assetRepository.save(newEntity);
                        dto = DTOMapper.assetToDto(newEntity);
                        System.out.println("Ny fil sparad i databasen: " + filename);
                    }

                    result.add(dto);
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;

    }
    public boolean deleteFileByAssetId(int assetId) {
        Optional<AssetEntity> optionalAsset = assetRepository.findById(assetId);

        if (optionalAsset.isEmpty()) {
            System.out.println("Inget asset hittades med id: " + assetId);
            return false;
        }

        AssetEntity asset = optionalAsset.get();
        String filename = asset.getFilename();

        try {
            // Ta bort från MEGA
            ProcessBuilder pb = new ProcessBuilder(
                    "C:\\Users\\johan\\AppData\\Local\\MEGAcmd\\MEGAclient.exe",
                    "rm", "/" + filename
            );
            Process process = pb.start();

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.out.println("Fel från MEGA: " + errorLine);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("Misslyckades med att ta bort från MEGA: " + filename);
                return false;
            }

            // Ta bort från databasen
            assetRepository.deleteById(assetId);
            System.out.println("Tog bort asset med id " + assetId + " och fil " + filename);
            return true;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


}