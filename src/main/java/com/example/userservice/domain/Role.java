package com.example.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity


@Table(name="Role")
@NoArgsConstructor
@AllArgsConstructor

public class Role  {

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<UserRoleProjet> getUserRoleProjet() {
		return UserRoleProjet;
	}
	public void setUserRoleProjet(List<UserRoleProjet> userRoleProjet) {
		UserRoleProjet = userRoleProjet;
	}
	@Override
	public String toString() {
		return this.getName().toString();
	}
	/**
	 *
	 */
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id ;

	@Column(unique=true)
	public String name ;
	@JsonIgnore
	@ManyToMany()
	@JoinTable(
			name = "role_role",
			joinColumns = @JoinColumn(name = "role_id"),
			inverseJoinColumns = @JoinColumn(name = "userrole_id"))
	public List<UserRoleProjet> UserRoleProjet=new ArrayList<UserRoleProjet>() ;



}
