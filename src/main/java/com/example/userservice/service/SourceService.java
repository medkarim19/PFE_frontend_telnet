package com.example.userservice.service;

import com.example.userservice.domain.*;
import java.util.List;



public interface SourceService {
    List<Source>getAllSource();
    void saveSouce (String valeur);
    Source findById(Long id) throws Exception;
    void saveAll();
}
