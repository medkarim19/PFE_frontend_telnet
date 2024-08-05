package com.example.userservice.domain;

import java.time.LocalDate

        ;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

//import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="Projet")
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Projet {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id ;
    @Column(unique=true)
    private String name;
    @Column(name="description")
    private String description ;
    @Column(name="nameofcreator")
    private String nameofcreator;

    @ElementCollection
    private Map<String, ArrayList<String>> usesswithroles=new HashMap<String,ArrayList<String>>();

    @Column(name="dateofcreation")
    private LocalDate dateofcreation;
    @OneToMany ( cascade = CascadeType.ALL, orphanRemoval = true)

    @JoinColumn(name="PROJET_ID")
    List <UserRoleProjet> list=new ArrayList<UserRoleProjet>() ;
    @OneToMany ( cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JoinColumn(name="PROJET_ID")
    List <Risque> list2=new ArrayList<Risque>() ;

}
