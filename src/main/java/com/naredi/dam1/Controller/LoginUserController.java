package com.naredi.dam1.Controller;
import com.naredi.dam1.DTO.SimpleNailpolishDto;
import com.naredi.dam1.Repositorys.AssetRepository;
import com.naredi.dam1.Repositorys.NailpolishRepository;
import com.naredi.dam1.services.MegaService;
import com.naredi.dam1.services.MySqlService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mega")
public class LoginUserController {

    private final MegaService megaService;
    private final NailpolishRepository nailpolishRepository;
    private final AssetRepository assetRepository;
    private final MySqlService mySqlService;

    @Autowired
    public LoginUserController(MegaService megaService,
                               NailpolishRepository nailpolishRepository,
                               AssetRepository assetRepository,
                               MySqlService mySqlService) {
        this.megaService = megaService;
        this.nailpolishRepository = nailpolishRepository;
        this.assetRepository = assetRepository;
        this.mySqlService = mySqlService;
    }

    @PutMapping("/nailpolish/{name}")
    public ResponseEntity<SimpleNailpolishDto> updateNailpolishByName(
            @PathVariable String name,
            @RequestBody SimpleNailpolishDto nailpolishDto) {
        try {
            SimpleNailpolishDto updated = mySqlService.updateNailpolishByName(name, nailpolishDto);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); //404
        }
    }

    @GetMapping("/user")
    public String userHello() {
        return "Hej användare!";
    }

    @GetMapping("/nailpolish")
    public List<SimpleNailpolishDto> listSimpleNailpolish() {
        return mySqlService.listSimpleNailpolish();
    }

    @GetMapping("/files")
    public List<String> getMegaAssets() {
        return megaService.listMegaAssets();
    }

}

