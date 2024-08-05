package com.example.userservice.service;

import com.example.userservice.domain.*;
import com.example.userservice.repo.ProjectRepo;
import com.example.userservice.repo.RisqueRepo;
import com.example.userservice.repo.RoleRepo;
import com.example.userservice.repo.UserRoleProjetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor

@Transactional
public class ProjetService {
    private final ProjectRepo projetrepo;
    @PersistenceContext // o
    EntityManager em;
    private final Userservice userservice;
    private final RoleRepo role;
    private final UserRoleProjetRepo userprojetrepo;
    private final  UserRoleProjetRepo userRoleProjetRepo;
    private final  RisqueRepo risquerepo ;

    public void Saveprojet(String nom, String description, String date, String nameofcreator) {

        LocalDate dt1 = LocalDate.parse(date);
        User user = userservice.getUser(nameofcreator);
        List<UserRoleProjet> list = new ArrayList<UserRoleProjet>();
        List<Risque> list22 = new ArrayList<Risque>();
        Projet projet1 = new Projet(null, nom, description, nameofcreator, null, dt1, list,list22);
        projetrepo.save(projet1);

        Role role1 = role.findByName("CHEF_PROJET");

        UserRoleProjet xx = new UserRoleProjet();
        xx.setP(projet1);
        xx.setUser(user);
        projet1.getList().add(xx);
        user.getList().add(xx);
        xx.getRole().add(role1);
        role1.getUserRoleProjet().add(xx);

        em.persist(xx);



    }
    public List<User>getAllusersinproject (Long id) throws Exception {
        List<User>list=new ArrayList<User>();
        List<UserRoleProjet>b=new ArrayList<>();
        b=projetrepo.findById(id)
                .map(p->p.getList())
                .orElseThrow(()->new Exception("projet not found"));
        b.forEach(x->list.add(x.user));
        return list;

    }
    public void addUserInProject (Long idofproject,Long idouser,List<String>list) throws Exception {
        Projet p=this.finprojectById(idofproject);
        User user=userservice.getUserById(idouser);
        List<Role>list1=new ArrayList<Role>();
        list.forEach(x->list1.add(role.findByName(x)));
        UserRoleProjet xx = new UserRoleProjet();
        xx.setP(p);
        xx.setUser(user);
        p.getList().add(xx);
        user.getList().add(xx);
        list1.forEach(x->xx.getRole().add(x));
        list1.forEach(x->x.getUserRoleProjet().add(xx));
        em.persist(xx);

    }

    public Projet finprojectById ( long idofproject) throws Exception{
        return	projetrepo.findById(idofproject)
                .orElseThrow(()->new Exception("projet not found"));

    }
    public List<User>getAllusersnotinproject (Long id) throws Exception {
        List<User>list=new ArrayList<User>();
        List<User>list2=new ArrayList<User>();
        List<UserRoleProjet>b=new ArrayList<>();
        b=projetrepo.findById(id)
                .map(p->p.getList())
                .orElseThrow(()->new Exception("projet not found"));
        b.stream().forEach(bb->list.add(bb.user));

        list2=userservice.getUser()
                .stream()
                .filter(u->!list.contains(u))
                .collect(Collectors.toList());
        return list2;

    }
    public void addAdmin (Long idouser) throws Exception {
        Projet p=this.finprojectById(-1);
        User user=userservice.getUserById(idouser);

        UserRoleProjet xx = new UserRoleProjet();
        xx.setP(p);
        xx.setUser(user);
        p.getList().add(xx);
        user.getList().add(xx);

        em.persist(xx);

    }
    public Long Updaterole(Long idofproject,Long idouser,List<String>list) throws Exception{



        UserRoleProjet cc=userRoleProjetRepo.findById(userRoleProjetRepo.find(idofproject, idouser)).orElseThrow();
        List<Role>list1=new ArrayList<Role>();
        List<Role>list2=new ArrayList<Role>();
        cc.getRole().forEach(x->list2.add(x));

        list.forEach(x->list1.add(role.findByName(x)));

        cc.getRole().forEach(a->a.getUserRoleProjet().remove(cc));
        list2.forEach(x->cc.getRole().remove(x));

        cc.setRole(list1);

        Long c=(long) 18;
        list1.forEach(x->x.getUserRoleProjet().add(cc));
        return (c);

    }
    public void deleteUserfromProjet (Long idofproject,Long idouser) throws Exception {
        UserRoleProjet cc=userRoleProjetRepo.findById(userRoleProjetRepo.find(idofproject, idouser)).orElseThrow();
        List<Role>list2=new ArrayList<Role>();
        cc.getRole().forEach(x->list2.add(x));
        Projet p=this.finprojectById(idofproject);
        User user=userservice.getUserById(idouser);
        p.getList().remove(cc);
        user.getList().remove(cc);
        cc.setP(null);
        cc.setUser(null);
        cc.setRole(null);
        list2.forEach(x->x.getUserRoleProjet().remove(cc));
        userprojetrepo.delete(cc);
    }
    public void deleteProjet (Long idofproject) throws Exception {
        List<Long>ids=userRoleProjetRepo.findids(idofproject);
        List<UserRoleProjet>ii=new ArrayList<UserRoleProjet>();
        List<Risque>uu=new ArrayList<Risque>();
        Projet p =this.finprojectById(idofproject);
        //List<Risque>b=p.getList2();
        List<Long>ids2=projetrepo.findrisque(idofproject);
        ids2.forEach(x->uu.add(risquerepo.findById(x).orElseThrow()));
        uu.forEach(x->{
            x.getType_deprojet().getListe().remove(x);
            x.getSource().getListe().remove(x);
            x.getProcessus_impacte().getListe().remove(x);
            x.getP().getList2().remove(x);
            x.setP(null);
            x.setType_deprojet(null);
            x.setSource(null);
            x.setProcessus_impacte(null);
            risquerepo.delete(x);

        });

        ids.forEach(x->ii.add(userRoleProjetRepo.findById(x).orElseThrow()));
        ii.forEach(x->{
            List<Role>bb=new ArrayList<Role>();
            bb=x.getRole();
            bb.stream().forEach(rr->rr.getUserRoleProjet().remove(x));
            x.setRole(null);
            x.getUser().getList().remove(x);
            x.getP().getList().remove(x);
            x.setP(null);
            x.setUser(null);
            userRoleProjetRepo.delete(x);
        });
        projetrepo.delete(this.finprojectById(idofproject));

    }
    public List<Role> roleuserinproject(Long idpro,Long iduser){
        UserRoleProjet cc=userRoleProjetRepo.findById(userRoleProjetRepo.find(idpro, iduser)).orElseThrow();
        List<Role>tt=new ArrayList<Role>();
        cc.getRole().forEach(x->tt.add(role.findById(x.id).orElseThrow()));
        return tt;
    }

}
