package com.naredi.dam1.Controller;
import com.naredi.dam1.DTO.NailpolishDTO;
import com.naredi.dam1.DTO.SimpleNailpolishDTO;
import com.naredi.dam1.Repositorys.AssetRepository;
import com.naredi.dam1.Repositorys.NailpolishRepository;
import com.naredi.dam1.services.MegaService;
import com.naredi.dam1.services.MySqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mega")
public class LoginUserController {

    private final MegaService megaService;

    @Autowired
    private MySqlService mySqlService;

    @Autowired
    public LoginUserController(MegaService megaService, NailpolishRepository nailpolishRepository, AssetRepository nailpolishFileRepository) {
        this.megaService = megaService;
    }

    @GetMapping("/user")
    public String userHello() {
        return "Hej användare!";
    }

    @GetMapping("/nailpolish")
    public List<SimpleNailpolishDTO> listSimpleNailpolish() {
        return mySqlService.listSimpleNailpolish();
    }

    @GetMapping("/files")
    public List<String> getMegaFiles() {
        return megaService.listMegaFiles();
    }

}

