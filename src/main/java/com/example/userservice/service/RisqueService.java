package com.example.userservice.service;


import com.example.userservice.domain.*;

import java.util.List;

public interface RisqueService {

    public Risque saveRisque(Risque r) throws Exception;
    public List<Risque> getAll();
    public Long deleterisque (Long id ) throws Exception;
    public List<Risque> gettAllrisqueforuser (Long id);
    public void saveorupdate(Long id , Impact i);
    // public void addplan_Action (Long id , )
    public List<chart> get_pourcentage();
    public List<chart2> calc1();
    public void update22 (Long id,int xx,int bb,String mesurerisque)throws Exception;
    public Risque getrisque (Long id) throws Exception;
    public Plan_Attenuation getPlan_Attunation (Long id )throws Exception;
    public Plan_Contingence getPlan_Contingence (Long id )throws Exception;
}
