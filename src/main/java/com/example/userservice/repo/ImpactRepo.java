package com.example.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.userservice.domain.Impact;


public interface ImpactRepo extends  JpaRepository<Impact,Long>  {
    //Impact findByValeur (String name );
}
