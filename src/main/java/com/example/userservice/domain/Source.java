package com.example.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="Source")

@NoArgsConstructor
@AllArgsConstructor
public class Source {
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getValeur() {
        return valeur;
    }
    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
    public List<Risque> getListe() {
        return liste;
    }
    public void setListe(List<Risque> liste) {
        this.liste = liste;
    }
    @Override
    public String toString() {
        return "Source [id=" + id + ", valeur=" + valeur + ", liste=" + liste + "]";
    }
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column (name="id")
    private Long id ;

    @Column (name="valeur",unique=true)
    private String valeur ;
    @JsonIgnore
    @OneToMany
    private List <Risque>liste=new ArrayList<Risque>();

}
