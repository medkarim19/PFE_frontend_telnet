package com.example.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Categorie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorieRisque {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id ;
    @Column (name="valeur",unique=true)
    private String valeur ;
    @JsonIgnore
    @OneToMany
    private List <Risque>liste=new ArrayList<Risque>();

}
