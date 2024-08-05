package com.example.userservice.service;

import com.example.userservice.domain.Action_Attenuation;
import com.example.userservice.domain.Plan_Attenuation;

import java.util.List;


public interface Plan_Attunation_Service {
    public void Add (Plan_Attenuation p) throws Exception ;
    public void update (Long id,String nom ) throws Exception;
    public Plan_Attenuation getByid(Long id) throws Exception;
    public void deletep (Long id ) throws Exception;
    public List <Action_Attenuation>getAll(Long id) throws Exception;
    public void delete(Long id) throws Exception;
}
