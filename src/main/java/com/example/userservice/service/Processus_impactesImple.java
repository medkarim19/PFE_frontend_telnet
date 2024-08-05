package com.example.userservice.service;

import com.example.userservice.domain.Processus_impactes;
import com.example.userservice.repo.Processus_impactesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor

@Transactional
public class Processus_impactesImple implements Processus_impactesService {
    private final Processus_impactesRepo Processus_impactesRepo;

    @Override
    public void add_Processus_impacte(String valeur, List<String> list) {
        Processus_impactes p = new Processus_impactes(null, valeur, null, null);
        Processus_impactesRepo.save(p);
        p.setList(list);

    }

    @Override
    public void add_all_Processus_impacte() {
        List<String> tt = new ArrayList<String>();
        String[] tab = {"MNG", "RCT", "PDI", "BDM", "PPM", "PRE", "DSS", "PRD", "HRM", "ISS", "PUR", "OQM", "BOS"};
        tt = Arrays.asList(tab);
        tt.forEach(x -> Processus_impactesRepo.save(new Processus_impactes(null, x, null, null)));


    }

    public Processus_impactes find(Long id) throws Exception {
        Processus_impactes p;
        return p = Processus_impactesRepo.findById(id).orElseThrow(() -> new Exception("not found"));
    }

    @Override
    public List<Processus_impactes> getAll() {
        // TODO Auto-generated method stub
        return Processus_impactesRepo.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Processus_impactes updateProcessus(Long id, String valeur, List<String> list) throws Exception {
        Processus_impactes p1 = find(id);
        p1.setValeur(valeur);
        p1.setList(list);
        return p1;
    }

    @Override
    public Processus_impactes getProcessus(Long id) throws Exception {
        Processus_impactes p = find(id);
        return p;
    }


    @Override
    public void deleleProcessus(Long id) throws Exception {
        Processus_impactes p = find(id);
        Processus_impactesRepo.delete(p);

    }

    @Override
    public List<String> getPhase(Long id) throws Exception {
        Processus_impactes p = find(id);
        List<String> list = p.getList();
        return list;
    }


}
