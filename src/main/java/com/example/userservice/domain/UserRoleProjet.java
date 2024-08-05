package com.example.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="UserRoleProjet")
@NoArgsConstructor

@AllArgsConstructor
public class UserRoleProjet {
    @Override
    public String toString() {
        return "UserRoleProjet [id=" + id + "]";
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Projet getP() {
        return p;
    }
    public void setP(Projet p) {
        this.p = p;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<Role> getRole() {
        return role;
    }
    public void setRole(List<Role> role) {
        this.role = role;
    }
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private Long id ;
    @ManyToOne ()
    @JsonIgnore
    public Projet p ;
    @ManyToOne()
    @JsonIgnore
    public User user ;
    @JsonIgnore
    @ManyToMany( targetEntity = com.example.userservice.domain.Role.class,mappedBy = "UserRoleProjet")

    public  List<Role> role=new ArrayList<Role>();



}
