package com.example.userservice.service;

import com.example.userservice.domain.Strategie;
import com.example.userservice.repo.Stratigieepo;
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
public class Stratigieserviceimpl implements StratigieService {
    private final Stratigieepo typerepo ;
    @Override
    public List<Strategie>getAllStratigie() {
        return typerepo.findAll().stream().collect(Collectors.toList());

    }

    @Override
    public void saveStratigie(String valeur,List<String> valeur2) {
        Strategie a= new Strategie (null,valeur,valeur2,null);
        typerepo.save(a);

    }

    @Override
    public Strategie findById(Long id) throws Exception {
        Optional<Strategie> type = Optional.ofNullable(typerepo.findById(id).orElseThrow(()->new Exception("Projet not found - " )));
        return type.get();

    }

    @Override
    public void saveAllStratigie() {
        ArrayList<String>tt=new ArrayList<String>();

        List<String>a1=new ArrayList<String>();
        a1.add("PROTECTION");
        a1.add("PREVENTION");
        typerepo.save(new Strategie (null,"REDUCTION",a1,null));
        List<String>a2=new ArrayList<String>();
        a2.add("REMPLACEMENT");
        a2.add("TRANSFERT");
        typerepo.save(new Strategie (null,"ERADICATION",a2,null));
        List<String>a3=new ArrayList<String>();
        a3.add("PROVISION");

        typerepo.save(new Strategie (null,"ACCEPTATION",a3,null));


    }
    @Override
    public void addactionToStratigie(Long id, List<String> b) throws Exception {
        Strategie staratigie= this.findById(id);
        b.forEach(x-> staratigie.getActions().add(x));


    }

}
