package com.example.userservice.service;

import com.example.userservice.domain.Action_Contingence;
import com.example.userservice.domain.Plan_Contingence;

import java.util.List;

public interface Plan_ContingenceService {
    public void savePlan (Plan_Contingence idrisque) throws Exception;
    public void update (Long id,String nom) throws Exception;
    public void deletePlan (Long id) throws Exception;
    public Plan_Contingence getByid(Long id) throws Exception;
    public List<Action_Contingence>getallactions(Long id) throws Exception;
}
