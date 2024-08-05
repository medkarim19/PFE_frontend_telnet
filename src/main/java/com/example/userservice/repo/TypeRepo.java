package com.example.userservice.repo;

import com.example.userservice.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepo extends  JpaRepository<Type,Long> {
    Type findByValeur(String name);
}
