package com.example.userservice.service;

import com.example.userservice.domain.Strategie;

import java.util.List;

public interface StratigieService {
    List<Strategie>getAllStratigie ();
    void saveStratigie (String valeur,List<String>val2);
    Strategie findById(Long id) throws Exception;
    void saveAllStratigie();
    void addactionToStratigie(Long id , List<String>b) throws Exception;
}
