package com.example.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="Impactes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Impact {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id ;
    private double r;

    private double r1;
    private double i;
    private double i1;
    private double s;
    private double s1;
    private double c ;
    private double c1;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "idrisque")
    private Risque risque ;




}
