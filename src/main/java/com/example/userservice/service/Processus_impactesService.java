package com.example.userservice.service;

import com.example.userservice.domain.Processus_impactes;

import java.util.List;

public interface Processus_impactesService {
    public void add_Processus_impacte(String valeur, List<String>list );
    public void add_all_Processus_impacte();
    public  Processus_impactes find(Long id) throws Exception;
    public List<Processus_impactes> getAll();
    public Processus_impactes updateProcessus(Long id,String valeur,List<String>list) throws Exception;
    public Processus_impactes getProcessus(Long id) throws Exception;
    public void deleleProcessus(Long id) throws Exception;
    public List<String> getPhase(Long id) throws Exception;

}
