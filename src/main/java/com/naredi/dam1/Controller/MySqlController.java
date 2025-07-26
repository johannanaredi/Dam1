package com.naredi.dam1.Controller;

import com.naredi.dam1.Entitys.AssetEntity;
import com.naredi.dam1.Repositorys.AssetRepository;
import com.naredi.dam1.services.MySqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mega")
public class MySqlController {

    @Autowired
    private AssetRepository assetRepository;

    @GetMapping("/assets")
    public List<AssetEntity> getAllAssets() {
        return assetRepository.findAll();
    }


}
