package com.naredi.dam1.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class MegaService {

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
}
