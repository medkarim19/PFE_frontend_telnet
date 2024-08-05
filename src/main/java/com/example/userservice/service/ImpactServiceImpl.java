package com.example.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

@RequiredArgsConstructor

@Transactional
public class ImpactServiceImpl implements ImpactService {
    //@NonNull private  final ImpactRepo impactRepo ;
    @Override
    public void saveImpact(String valeur) {


    }

    @Override
    public void saveAllImpact() {
        // TODO Auto-generated method stub

    }

}
