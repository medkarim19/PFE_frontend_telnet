package com.example.userservice.service;

import com.example.userservice.domain.Type;
import com.example.userservice.repo.TypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor

@Transactional

public class TypeServiceImple implements TypeService {
    private final TypeRepo typerepo;

    @Override
    public List<Type> getAllTypes() {
        return typerepo.findAll().stream().collect(Collectors.toList());

    }

    @Override
    public void saveType(String valeur) {
        Type a = new Type(null, valeur, null);
        typerepo.save(a);

    }

    public void saveAllTyper() {
        ArrayList<String> tt = new ArrayList<String>();
        tt.add("Projet");
        tt.add("Processus");
        tt.add("Analyse de contexte");
        tt.forEach(x -> typerepo.save(new Type(null, x, null)));


    }

    @Override
    public Type findById(Long id) throws Exception {

        Optional<Type> type = Optional.ofNullable(typerepo.findById(id).orElseThrow(() -> new Exception("Projet not found - ")));
        return type.get();


    }

}
