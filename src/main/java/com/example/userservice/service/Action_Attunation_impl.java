package com.example.userservice.service;

import com.example.userservice.domain.Action_Attenuation;
import com.example.userservice.repo.Action_Attenuationrepo;
import com.example.userservice.repo.Plan_Attunationrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service

@RequiredArgsConstructor

@Transactional
public class Action_Attunation_impl implements Action_AttunationService {
    private final Action_Attenuationrepo repo ;
    private final Plan_Attunationrepo pl ;
    private final Userservice userservice ;
    @Override
    public void saveAction(Action_Attenuation c) throws Exception {

        repo.save(c);
        System.out.println ( c+"ppp");
        pl.findById(c.getPlan().getId()).orElseThrow(()->new Exception ("not ound")).getList().add(c);
        userservice.getUserById(c.getA().getId()).getList1().add(c);
        //c.getA().getList1().add(c);
        //c.getPlan().getList().add(c);

    }
    public void update ( Long id ,Action_Attenuation c) throws Exception {
        System.out.println("ACTIONN : "+c);
        Action_Attenuation action= repo.findById(id).orElseThrow(()->new Exception ("action not found"));
        action.getA().getList().remove(action);
        action.setNom(c.getNom());
        action.setEtatdaction(c.getEtatdaction());
        action.setDatederealisation(c.getDatederealisation());
        action.setEfficatite(c.getEfficatite());
        action.setDateevalation(c.getDateevalation());
        action.setDateplanifie(c.getDateplanifie());
        action.setA(c.getA());
        c.getA().getList1().add(action);
    }

    @Override
    public void deleteAction(Long id) throws Exception {
        Action_Attenuation action= repo.findById(id).orElseThrow(()->new Exception ("action not found"));
        System.out.println ("id"+action.getId());

        action.getA().getList1().remove(action);
        action.getPlan().getList().remove(action);
        action.setA(null);
        action.setPlan(null);
        repo.delete(action);

    }



}
