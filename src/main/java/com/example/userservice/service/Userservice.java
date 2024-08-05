package com.example.userservice.service;

import com.example.userservice.domain.Projet;
import com.example.userservice.domain.Role;
import com.example.userservice.domain.User;

import java.util.List;
import java.util.Map;

public interface Userservice {
User saveUser (User user);
Role saveRole (Role role);
void addRoleToUser (String username ,String roleName);
User getUser (String username);
Role getRole(String name);
List <User>getUser ();
User updateUser(User user, String username);
Role updateRole(Role role,String name);
public void deleteUser(Long id) throws Exception;
List<Role> getRole();
Map<String, Boolean> deleteRole(String name);
List<Projet> getAllProjet(Long id);
public String generatePassword(int min, int max);
public void updateResetPasswordToken(String token, String email);
public Long getByResetPasswordToken(String token);
public void updatePassword(User user, String newPassword);
Boolean isAdmin (Long id);
User getUserById (Long id) throws Exception;
public void changepassword(Long id,String oldpassword,String newpassword) throws Exception;
    public  void updatedetails(Long id ,User user) throws Exception;
}
