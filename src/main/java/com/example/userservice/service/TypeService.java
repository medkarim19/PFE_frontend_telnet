package com.example.userservice.service;

import com.example.userservice.domain.Type;

import java.util.List;

public interface TypeService {
    List<Type>getAllTypes ();
    void saveType (String valeur);
    Type findById(Long id) throws Exception;
    void saveAllTyper();
}
