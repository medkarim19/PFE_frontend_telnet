package com.example.userservice.repo;

import com.example.userservice.domain.Plan_Attenuation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface  Plan_Attunationrepo extends  JpaRepository<Plan_Attenuation,Long>  {
    @Query(value = "select id  from action_attenuation where id_action =?1",nativeQuery = true)
    public List<Long> find(Long id);
}
