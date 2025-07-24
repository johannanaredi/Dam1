package com.naredi.dam1.Controller;


import com.naredi.dam1.services.MegaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mega")
public class MegaController {

    private final MegaService megaService;

    @Autowired
    public MegaController(MegaService megaService) {
        this.megaService = megaService;
    }

    @GetMapping("/files")
    public List<String> getMegaFiles() {
        return megaService.listMegaFiles();
    }

}
