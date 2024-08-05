package com.example.userservice.domain;

import com.example.userservice.service.ImageModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity

@Table(name="User")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class User  {
	@Override
	public String toString() {
		return String.valueOf(this.id);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	public List<Projet> getListe() {
		return liste;
	}
	public void setListe(List<Projet> liste) {
		this.liste = liste;
	}
	public List<UserRoleProjet> getList() {
		return list;
	}
	public void setList(List<UserRoleProjet> list) {
		this.list = list;
	}

	public List<Action_Attenuation> getList1() {
		return list1;
	}

	public void setList1(List<Action_Attenuation> list1) {
		this.list1 = list1;
	}

	public List<Action_Contingence> getList2() {
		return list2;
	}

	public void setList2(List<Action_Contingence> list2) {
		this.list2 = list2;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public List < Plan_Contingence> getList13() { return list13;}

	public List <Plan_Attenuation> getList11() { return list11;}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id ;
	private  String name ;
	private String lastname ;
	@Column(unique=true)
	private  String username;
	private  String password ;
	private String email ;
	private String resetPasswordToken;

	@ManyToMany(fetch =FetchType.EAGER)
	private  Collection<Role>roles=new ArrayList<>();
	@ManyToMany
	List<Projet>liste;
	@OneToMany

	@JoinColumn(name = "userid")
	List <UserRoleProjet> list=new ArrayList<UserRoleProjet>() ;
	@OneToMany

	@JoinColumn(name = "userid")
	@JsonIgnore
	List <Action_Attenuation> list1=new ArrayList<Action_Attenuation>() ;

	@OneToMany

	@JoinColumn(name = "userid")
	List <Action_Contingence> list2=new ArrayList<Action_Contingence>() ;
	@OneToMany
	@JsonIgnore
	@JoinColumn(name = "userid")
	List <Plan_Attenuation> list11=new ArrayList<Plan_Attenuation>() ;

	@OneToMany
	@JsonIgnore
	@JoinColumn(name = "userid")
	List < Plan_Contingence> list13=new ArrayList <Plan_Contingence>() ;

	@OneToOne
	@JsonIgnore

	private ImageModel img ;


	public void setImg(ImageModel img) { this.img=img;}

	public ImageModel getImg() { return img;}
}
