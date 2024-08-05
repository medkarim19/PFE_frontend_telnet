package com.example.userservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name="Plan_Contingence")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan_Contingence {
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private Long  id ;
    @Column (name="nomdeplan")
    private String nom ;
    @OneToMany
    @JoinColumn(name = "id_Action")
    private List <Action_Contingence> list ;
    @OneToOne
    Risque r;
    @ManyToOne ()
    User a ;




}
