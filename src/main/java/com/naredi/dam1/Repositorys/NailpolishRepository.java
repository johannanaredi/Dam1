
package com.naredi.dam1.Repositorys;

import com.naredi.dam1.Entitys.NailpolishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NailpolishRepository extends JpaRepository<NailpolishEntity, Integer> {
    boolean existsByName(String name);
    Optional<NailpolishEntity> findByName(String name);
}
