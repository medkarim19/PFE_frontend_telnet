package com.example.userservice.service;

import com.example.userservice.domain.Action_Contingence;
import com.example.userservice.repo.Action_contongecerepo;
import com.example.userservice.repo.Plan_ContingenceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

@RequiredArgsConstructor

@Transactional
public class Action_Contingence_impl implements Action_ContingenceService  {
    private final Action_contongecerepo repo ;
    private final Plan_ContingenceRepo pl ;
    private final Userservice userservice ;






    @Override
    public void deleteAction(Long id) throws Exception {

        Action_Contingence action= repo.findById(id).orElseThrow(()->new Exception ("action not found"));
        action.getB().getList2().add(action);
        action.getPlan().getList().add(action);
        action.setB(null);
        action.setPlan(null);
        repo.delete(action);
    }


    @Override
    public void saveAction(Action_Contingence c) throws Exception {
        repo.save(c);
        System.out.println ( c+"ppp");
        pl.findById(c.getPlan().getId()).orElseThrow(()->new Exception ("not ound")).getList().add(c);
        userservice.getUserById(c.getB().getId()).getList2().add(c);
        //c.getA().getList1().add(c);


    }

}
