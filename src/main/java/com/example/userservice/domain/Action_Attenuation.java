package com.example.userservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="Action_Attenuation")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Action_Attenuation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id ;
    @Column(name="nomAction1")
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
    private String efficatite ;
    @ManyToOne


    private  Plan_Attenuation  plan ;
    @ManyToOne (targetEntity = com.example.userservice.domain.User.class)

    private  User a ;






}
