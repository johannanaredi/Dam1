package com.naredi.dam1.Controller;
import com.naredi.dam1.DTO.DTOMapper;
import com.naredi.dam1.DTO.NailpolishDTO;
import com.naredi.dam1.DTO.SimpleNailpolishDTO;
import com.naredi.dam1.Entitys.NailpolishEntity;
import com.naredi.dam1.Repositorys.AssetRepository;
import com.naredi.dam1.Repositorys.NailpolishRepository;
import com.naredi.dam1.services.MegaService;
import com.naredi.dam1.services.MySqlService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PutMapping("/nailpolish/{name}")
    public ResponseEntity<SimpleNailpolishDTO> updateNailpolishByName(
            @PathVariable String name,
            @RequestBody SimpleNailpolishDTO nailpolishDto) {
        try {
            SimpleNailpolishDTO updated = mySqlService.updateNailpolishByName(name, nailpolishDto);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
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

