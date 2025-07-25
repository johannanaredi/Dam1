package com.naredi.dam1.Repositorys;

import com.naredi.dam1.Entitys.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<AssetEntity, Integer> {

}
