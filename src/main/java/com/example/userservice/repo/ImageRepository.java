package com.example.userservice.repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.userservice.service.*;

public interface ImageRepository extends JpaRepository<ImageModel, Long>{
    Optional<ImageModel> findByName(String name);
}
