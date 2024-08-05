package com.example.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name="Plan_Attenuation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan_Attenuation {
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private Long  id ;
    @Column (name="nomdeplan")
    private String nom ;
    @OneToMany
    @JoinColumn(name = "idAction")
    @JsonIgnore
    private List <Action_Attenuation> list ;
    @OneToOne
    Risque r;

    @ManyToOne
    User a ;
}
