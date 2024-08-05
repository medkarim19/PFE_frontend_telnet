package com.example.userservice.service;

import com.example.userservice.domain.Source;
import com.example.userservice.repo.SourceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service

@RequiredArgsConstructor

@Transactional
public class Sourceimpl implements SourceService{
    private final SourceRepo sourcerepo;
    @Override
    public List<Source> getAllSource() {
        return sourcerepo.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public void saveSouce(String valeur) {
        Source source = new Source (null,valeur,null);
        sourcerepo.save(source);

    }
    @Override
    public void saveAll() {

        ArrayList<String>tt=new ArrayList<String>();
        tt.add("Contractuel");
        tt.add("Reglementaire");
        tt.add("Technique");
        tt.add("Organisationel");
        tt.add("Humain");
        tt.forEach(x->sourcerepo.save(new Source (null,x,null)));




    }

    @Override
    public Source findById(Long id) throws Exception {
        Source b= sourcerepo.findById(id).orElseThrow();
        return b;
    }

}
