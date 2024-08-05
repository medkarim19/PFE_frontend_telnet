package com.example.userservice.repo;

import com.example.userservice.domain.Action_Contingence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  Action_contongecerepo extends  JpaRepository<Action_Contingence,Long>  {
    //Impact findByValeur (String name );
}
