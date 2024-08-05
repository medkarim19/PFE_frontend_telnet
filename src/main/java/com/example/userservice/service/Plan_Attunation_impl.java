package com.example.userservice.service;

import com.example.userservice.domain.Action_Attenuation;
import com.example.userservice.domain.Plan_Attenuation;
import com.example.userservice.repo.Plan_Attunationrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@RequiredArgsConstructor


public class Plan_Attunation_impl implements Plan_Attunation_Service  {
    private final Plan_Attunationrepo repo ;

    private final Action_AttunationService rr;
    private final RisqueService  risqueservice ;
    private final Userservice userservice ;

    @Override
    public void Add(Plan_Attenuation p) throws Exception {
        repo.save(p);
        System.out.println("PLANNNNNNN :"+p);
        System.out.println (p.getR()+"nnnnn");
        risqueservice.getrisque(p.getR().getId()).setB(p);
        userservice.getUserById(p.getA().getId()).getList11().add(p);
    }




    @Override
    public void deletep(Long id) throws Exception {

        Plan_Attenuation p=repo.findById(id).orElseThrow(()->new Exception ("not found"));
        List <Long>tt=repo.find(p.getId());
        tt.forEach(x->{

            try {
                rr.deleteAction(x);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });
        p.getR().setB(null);
        p.setR(null);
        p.getA().getList11().remove(p);
        p.setA(null);
        repo.delete(p);

    }
    @Override
    public void delete(Long id) throws Exception {
        Long x=risqueservice.deleterisque(id);
        this.deletep(x);

    }

    @Override
    public List<Action_Attenuation> getAll(Long id) throws Exception {
        Plan_Attenuation p=repo.findById(id).orElseThrow(()->new Exception ("not found"));
        return p.getList();
    }

    @Override
    public Plan_Attenuation getByid(Long id) throws Exception {
        return repo.findById(id).orElseThrow(()->new Exception ("not found "));

    }




    @Override
    public void update(Long id,String nom) throws Exception {
        Plan_Attenuation p= this.getByid(id);
        p.setNom(nom);

    }


}
