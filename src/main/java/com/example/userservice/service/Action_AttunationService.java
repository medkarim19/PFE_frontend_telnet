package com.example.userservice.service;

import com.example.userservice.domain.Action_Attenuation;

public interface Action_AttunationService {
    public void saveAction (Action_Attenuation c) throws Exception;
    public void deleteAction (Long id ) throws Exception;
    public void update (Long id ,Action_Attenuation c) throws Exception ;


}