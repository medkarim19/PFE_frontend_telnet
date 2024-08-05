package com.example.userservice.service;

import com.example.userservice.domain.*;
import com.example.userservice.repo.ImpactRepo;
import com.example.userservice.repo.RisqueRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
@Service

@RequiredArgsConstructor

@Transactional

public class Risqueserviceimpl implements  RisqueService {
    private final ProjetService pp;
    private final RisqueRepo risquerepo;
    private final Processus_impactesService ppk;
    private final TypeService typeservice ;
    private final StratigieService stratigieservice ;
    private final SourceService sourceservice ;
    private final Userservice userservice;
    private final ImpactRepo impactrepo ;
    private final CategorieService cat;


    @Override
    public Risque saveRisque(Risque r) throws Exception {
        System.out.println (r+"oooooo");
        System.out.println(r.getSource());
        ZoneId zid = ZoneId.of("Europe/Paris");

        // create an LocalDate object using now(zoneId)
        LocalDateTime lt
                = LocalDateTime.now();
        Risque risq= new Risque (
                null,
                r.getName(),
                r.getP(),
                r.getNomProprieterderisque(),
                r.getType_deprojet(),
                r.getSource(),
                r.getCauses(),
                r.getConsequences(),
                r.getProcessus_impacte(),
                r.getMesurepreventive(),
                0,
                0,
                0,
                null,
                r.getEtatderisque(),
                lt,
                r.getC(),
                r.getS(),
                null,null,r.getPhase());

        //Risque risq= new Risque (null,r.getName(),r.getP(),r.getNomProprieterderisque(),r.getType_deprojet(),r.getSource(),r.getCauses(),r.getConsequences(),r.getProcessus_impacte(),r.getMesurepreventive(),r.getGravite(),r.getProbalite(),r.getCriticite(),null,r.getEtatderisque(),r.getGravite1(),r.getProbalite1(),r.getCriticite1(),null,null,null);
        risquerepo.save(risq);
        CategorieRisque cc=cat.findById(risq.getC().getId()); cc.getListe().add(risq);
        Projet a =pp.finprojectById(risq.getP().getId());a.getList2().add(risq);
        Strategie p = stratigieservice.findById(risq.getS().getId());  p.getListe().add(risq);
        Processus_impactes ww =ppk.find(risq.getProcessus_impacte().getId());ww.getListe().add(risq);
        Type ab=typeservice.findById(risq.getType_deprojet().getId());//ab.getListe().add(risq);
        Source b= sourceservice.findById(risq.getSource().getId()); b.getListe().add(risq);
        System.out.print("le risque est : "+r.getPhase());
        //Stratigie xx= stratigieservice.findById(risq.getStratigie().getId());xx.getListe().add(risq);
        return risq;




    }


    @Override
    public List<Risque> getAll() {

        return risquerepo.findAll();
    }


    @Override
    public Long deleterisque(Long id) throws Exception {
        Risque r=	risquerepo.findById(id).orElseThrow();
        Long x=null;


        r.getP().getList2().remove(r);
        r.getS().getListe().remove (r);
        r.getC().getListe().remove(r);
        //r.getType_deprojet().getListe().remove(r);
        r.getSource().getListe().remove(r);
        r.getProcessus_impacte().getListe().remove (r);
        //r.getA().setR(null);
        if (r.getB()!=null) {
            x=r.getB().getId();
        r.getB().setR(null);
            r.setB(null);}
        //r.getStratigie().getListe().remove(r);
        r.setP(null);
        r.setS(null);
        r.setC(null);
        r.setType_deprojet(null);
        r.setSource(null);
        r.setProcessus_impacte(null);
        //r.setA(null);

        //r.setStratigie(null);
        if (r.getImpact()!=null) {

            r.getImpact().setRisque(null);
            impactrepo.delete(r.getImpact());
            r.setImpact(null);



        }

        risquerepo.delete(r);

        return x;

    }


    @Override
    public List<Risque> gettAllrisqueforuser(Long id) {
        if (userservice.isAdmin(id))return risquerepo.findAll();
        else {
            return risquerepo.findrisqueuser(id);
        }


    }
    @Override
    public Plan_Attenuation getPlan_Attunation(Long id) throws Exception {
        return this.getrisque(id).getB();

    }


    @Override
    public Plan_Contingence getPlan_Contingence(Long id) throws Exception {
        return null ;
        //return this.getrisque(id).getA();
    }

    public void saveorupdate (Long  id ,Impact t) {
        Risque r=	risquerepo.findById(id).orElseThrow();
        if (r.getImpact()!=null	) {
            System.out.println ("loll");
            r.getImpact().setR(t.getR());
            r.getImpact().setR1(t.getR1());
            r.getImpact().setI(t.getI());
            r.getImpact().setI1(t.getI1());
            r.getImpact().setS(t.getS());
            r.getImpact().setS1(t.getS1());
            r.getImpact().setC(t.getC());
            r.getImpact().setC1(t.getC1());
        }
        else
        {
            impactrepo.save(t);
            t.setRisque(r);
            r.setImpact(t);
        }
    }
    @Override
    public List<chart> get_pourcentage() {
        List<chart> ppp=new ArrayList<chart>() ;
        List<Processus_impactes>oo=new ArrayList<Processus_impactes>();
        oo=ppk.getAll();
        oo.forEach(p -> {
            Double b= risquerepo.testfindrisque(p.getId());
            Double nb_risque= risquerepo.calcul();
            Double nb= b/nb_risque;
            System.out.println(nb);
            if(b>0){
            chart c=new chart(p.getValeur(),nb*100);
            ppp.add(c);}
        });
        return ppp;
    }
    @Override
    public List<chart2> calc1() {
        List<chart2> ppp=new ArrayList<chart2>() ;
        Double tab[]=new Double[1];
        Double tab1[]=new Double[1];
        Double tab2[]=new Double[1];
        tab[0]=risquerepo.calculAcceptable();
        System.out.println("aaaaaaa"+tab[0]);
        chart2 t=new chart2("Acceptable",tab);
        ppp.add(t);
        tab1[0]=risquerepo.calculSurveiller();
        System.out.println("bbbbbbbbbbbb"+tab1[0]);
        chart2 t1=new chart2("Surveiller",tab1);
        ppp.add(t1);
        tab2[0]=risquerepo.calculInacceptable();
        System.out.println("cccccccccccc"+tab2[0]);
        chart2 t2=new chart2("Inacceptable",tab2);
        ppp.add(t2);
        return ppp;

    }
    @Override
    public Risque getrisque(Long id) throws Exception {
        return	risquerepo.findById(id).orElseThrow(()->new Exception ("not found"));

    }
    @Override
    public void update22(Long id, int probalite ,int gravite ,String mesurerisque) throws Exception {

        Risque tt=this.getrisque(id);
        System.out.println("date"+ LocalDate.now());;
        tt.setModified(LocalDateTime.now());

        tt.setProbalite(probalite);
        tt.setGravite(gravite);
        tt.setCriticite(gravite*probalite);
        tt.setMesurerisque(mesurerisque);

    }


}
