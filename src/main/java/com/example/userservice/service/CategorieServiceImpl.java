package com.example.userservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.userservice.domain.CategorieRisque;
import com.example.userservice.repo.CategorieRisquerepo;
import com.example.userservice.repo.TypeRepo;

import lombok.RequiredArgsConstructor;
@Service

@RequiredArgsConstructor

@Transactional
public class CategorieServiceImpl implements CategorieService {
    private final CategorieRisquerepo tess;
    @Override
    public List<CategorieRisque> getAllCategorie() {

        return tess.findAll();
    }

    @Override
    public void saveCategorie(String valeur) {
        tess.save(new CategorieRisque (null,valeur,null) );

    }

    @Override
    public CategorieRisque findById(Long id) throws Exception {
        return tess.findById(id).orElseThrow();

    }

    @Override
    public void saveAll() {
        ArrayList<String>nn=new ArrayList<String>();



        nn.add("Manque de ressources formées techniquement");
        nn.add("Instabilité des exigences");
        nn.add("Manque de ressource");
        nn.add("Délais irréalisables");
        nn.add("Logiciel, matériel requis non disponible");
        nn.add("Nouvelle technologie");
        nn.add("Connaissance du domaine insuffisante");
        nn.add("Couverture des tests inadéquate");
        nn.add("Environnement de déploiement non disponible");
        nn.add("Sécurité de l'information");
        nn.add(" Autres");
        nn.forEach(x->tess.save  (new CategorieRisque(null,x,null)));

    }

}
