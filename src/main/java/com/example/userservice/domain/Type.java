package com.example.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity


@Table(name="Type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column (name="id")
    private Long id ;

    @Column (name="valeur",unique=true)
    private String valeur ;
    @OneToMany
    @JsonIgnore
    private List <Risque>liste;
}
