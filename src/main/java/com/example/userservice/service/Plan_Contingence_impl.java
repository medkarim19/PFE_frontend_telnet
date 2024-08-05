package com.example.userservice.service;

import com.example.userservice.domain.Action_Contingence;
import com.example.userservice.domain.Plan_Contingence;
import com.example.userservice.repo.Plan_ContingenceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

@RequiredArgsConstructor

@Transactional
public class Plan_Contingence_impl implements Plan_ContingenceService {
    private final Plan_ContingenceRepo repo;

    private final RisqueService  risqueservice ;
    private final Userservice userservice ;
    private  Action_ContingenceService rpo  ;
    @Override
    public void savePlan(Plan_Contingence p) throws Exception {

        //repo.save(p);

        //System.out.println (p.getR()+"nnnnn");
        //risqueservice.getrisque(p.getR().getId()).setA(p);





        userservice.getUserById(p.getA().getId()).getList13().add(p);
    }
    @Override
    public void deletePlan(Long id) throws Exception {
        Plan_Contingence p=repo.findById(id).orElseThrow(()->new Exception ("not found"));
        List <Long>tt=repo.find(p.getId());
        tt.forEach(x->{
            try {
                rpo.deleteAction(x);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        //p.getR().setA(null);;
        p.setR(null);
        p.getA().getList13().remove(p);
        p.setA(null);
        repo.delete(p);;;

    }
    @Override
    public List<Action_Contingence> getallactions(Long id) throws Exception {
        Plan_Contingence p=repo.findById(id).orElseThrow(()->new Exception ("not found"));
        return p.getList();
    }
    @Override
    public Plan_Contingence getByid(Long id) throws Exception {

        return repo.findById(id).orElseThrow(()->new Exception ("not found "));
    }
    @Override
    public void update(Long id,String nom) throws Exception {
        Plan_Contingence p=this.getByid(id);
        p.setNom(nom);

    }


}
