package com.example.userservice.repo;

import com.example.userservice.domain.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepo extends JpaRepository<Source,Long> {
    Source findByValeur (String name );

}
