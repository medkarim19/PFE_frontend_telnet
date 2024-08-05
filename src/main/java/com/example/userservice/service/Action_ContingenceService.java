package com.example.userservice.service;

import com.example.userservice.domain.Action_Contingence;

public interface Action_ContingenceService {
    public void saveAction (Action_Contingence c) throws Exception;
    public void deleteAction (Long   id ) throws Exception;
}
