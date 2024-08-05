package com.example.userservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table (name="Action_Contingence")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action_Contingence {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id ;
    @Column(name="nom_Action1")
    private String nom ;
    @Column(name="date_planifie")
    private LocalDate dateplanifie;
    @Column(name="date_realisation")
    private LocalDate datederealisation;
    @Column (name="etat_daction")
    private String etatdaction;
    @Column (name="Date_devalation")
    private LocalDate dateevalation;
    @Column (name="effiacatite")
    private Boolean efficatite ;
    @Column (name="Responsable")
    private String nom_responsable ;
    @ManyToOne
    @JoinColumn(name ="plan")
    private Plan_Contingence  plan ;
    @ManyToOne
    @JoinColumn(name = "plan1")

    private  User b ;

}
