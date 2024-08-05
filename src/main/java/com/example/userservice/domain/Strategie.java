package com.example.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Entity


@Table(name="Stratigie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Strategie {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column (name="id")
    private Long id ;

    @Column (name="valeur",unique=true)
    private String valeur ;
    @ElementCollection
    List<String>liste_Actions;
    @OneToMany
    @JsonIgnore
    private List <Risque>liste;

    public  List<String> getActions() { return liste_Actions;}

}
