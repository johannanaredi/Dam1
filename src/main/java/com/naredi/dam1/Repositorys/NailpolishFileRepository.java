package com.naredi.dam1.Repositorys;

import com.naredi.dam1.Entitys.NailpolishFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NailpolishFileRepository extends JpaRepository<NailpolishFileEntity, Integer> {

}
