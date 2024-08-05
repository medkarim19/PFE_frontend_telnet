package com.example.userservice.repo;

import com.example.userservice.domain.Action_Attenuation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  Action_Attenuationrepo extends  JpaRepository<Action_Attenuation,Long>  {
    //Impact findByValeur (String name );
}
