package com.example.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Risque")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Risque  {
    @Override
    public String toString() {
        return "Risque [id=" + id + ", name=" + name + ",]";
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id ;
    @Column (name="nomderisque")
    private String name;
    @ManyToOne  @JoinColumn( name="idprojet" )
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)

    private Projet p;
    @Column (name="nomProprieterderisque")
    private String nomProprieterderisque;

    @ManyToOne @JoinColumn( name="idtype" )
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Type type_deprojet;
    @ManyToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @JoinColumn( name="id_source" )
    private Source source;
    @ElementCollection
    @Column (name="causes")
    private List<String> causes ;
    @ElementCollection
    @Column (name="consequences")
    private List<String> consequences ;
    @ManyToOne  @JoinColumn( name="Processusimpacte" )
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Processus_impactes processus_impacte;
    @Column (name="MesurePreventives")
    private String mesurepreventive ;
    @Column (name="Gravite")
    private int gravite;
    @Column (name="Probalite")
    private int probalite;
    @Column (name="Criticite")
    private int criticite;
    @OneToOne

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Impact impact;
    @Column (name="etat")
    private String  etatderisque;

    //@Column (name="Gravite1")
    //private int gravite1;
    //@Column (name="Probalite1")
    //private int probalite1;
    //@Column (name="Criticite1")
//	private int criticite1;
    //@ManyToOne
    //@JoinColumn( name="stratigie_id" )
    //private Stratigie stratigie ;

    @LastModifiedDate

    private  LocalDateTime modified;
    //@Column (name="type_action")
    //private String type_Action ;
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne  @JoinColumn( name="categorie" )
    private CategorieRisque c ;
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne  @JoinColumn( name="Stratigie" )
    private  Strategie s ;

    @OneToOne
    @JsonIgnore
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Plan_Attenuation b;
    private String mesurerisque ;
    @Column (name="Phases")
    private String phase;

    //@OneToOne
    //@JsonIgnore
    // @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    //private Plan_Contingence a;

}



