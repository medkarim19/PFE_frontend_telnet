package com.example.userservice.service;

import com.example.userservice.domain.CategorieRisque;

import java.util.List;

public interface CategorieService {
    List<CategorieRisque>getAllCategorie();
    void saveCategorie (String valeur);
    CategorieRisque findById(Long id) throws Exception;
    void saveAll();
}
