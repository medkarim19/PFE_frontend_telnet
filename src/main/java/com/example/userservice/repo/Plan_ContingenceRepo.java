package com.example.userservice.repo;

import com.example.userservice.domain.Plan_Contingence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Plan_ContingenceRepo extends  JpaRepository<Plan_Contingence,Long>  {
    //Impact findByValeur (String name );
    @Query(value = "select id  from  action_contingence where id_action =?1",nativeQuery = true)
    public List<Long> find(Long id);
}
