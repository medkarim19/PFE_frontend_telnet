package com.example.userservice.service;

import com.example.userservice.domain.Projet;
import com.example.userservice.domain.Role;
import com.example.userservice.domain.User;
import com.example.userservice.domain.UserRoleProjet;
import com.example.userservice.repo.ProjectRepo;
import com.example.userservice.repo.RoleRepo;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.repo.UserRoleProjetRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor

@Transactional
@Slf4j
public class Userserviceimple implements Userservice, UserDetailsService {
    @NonNull
    private final UserRepo UserRepo;
    @NonNull
    private final RoleRepo rolee;
    @NonNull
    private final ProjectRepo p;
    @NonNull
    private final UserRoleProjetRepo userrole;
    @NonNull
    private final PasswordEncoder password;
    private final BCryptPasswordEncoder pass;
    public static String pwd;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = UserRepo.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database ");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("user found", username);

        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);

    }

    @Override
    public User saveUser(User user) {
        String generatePassword = generatePassword(8, 16);
        log.info("saving a new user to the database", user.getUsername());
        System.out.println("mot de passe : " + generatePassword);
        this.pwd = generatePassword;
        user.setPassword(password.encode(generatePassword));

        return UserRepo.save(user);
    }


    @Override
    public Role saveRole(Role role) {
        log.info("saving a new Role to the database", role.getName());
        return rolee.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding   a new Role to the user", roleName, username);
        User user = UserRepo.findByUsername(username);
        Role role = rolee.findByName(roleName);
        user.getRoles().add(role);

    }

    @Override
    public User getUser(String username) {
        log.info("fetching user {}", username);
        return UserRepo.findByUsername(username);

    }

    @Override
    public Role getRole(String name) {
        log.info("fetching role {}", name);
        return rolee.findByName(name);
    }

    @Override
    public List<Role> getRole() {
        log.info("fetching ALL user");
        return rolee.findAll();
    }


    @Override
    public List<User> getUser() {
        log.info("fetching ALL user");
        return UserRepo.findAll();
    }

    @Override
    public User updateUser(User user, String username) {
        User user1 = getUser(username);
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setName(user.getName());
        user1.setPassword(password.encode(user1.getPassword()));
        return user1;

    }

    @Override
    public Role updateRole(Role role, String name) {
        Role role1 = getRole(name);
        role1.setName(role.getName());
        rolee.save(role1);
        return role1;

    }

    @Override
    public Map<String, Boolean> deleteRole(String name) {
        Role role = getRole(name);
        rolee.delete(role);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @Override
    public Boolean isAdmin(Long id) {
        if (userrole.find((long) -1, id) == null) {
            return false;
        }
        return true;
    }

    @Override
    public User getUserById(Long id) throws Exception {
        return
                UserRepo.findById(id).orElseThrow(() -> new Exception("user not found"));
    }

    @Override
    public List<Projet> getAllProjet(Long id) {
        if (this.isAdmin(id)) {
            p.findAll().stream().filter(pp -> pp.getId() > -1).collect(Collectors.toList());
            return p.findAll().stream().filter(pp -> pp.getId() > -1).collect(Collectors.toList());
        } else {
            List<Long> ll = userrole.findprojet(id);
            List<Projet> pp = new ArrayList<Projet>();
            ll.forEach(oo -> pp.add(p.findById(oo).orElseThrow()));
            return pp;
        }

    }

    @Override
    public void deleteUser(Long id) throws Exception {
        System.out.println("user id = " + id);
        List<Long> ids = userrole.findids2(id);

        List<UserRoleProjet> ii = new ArrayList<UserRoleProjet>();
        if (!ids.isEmpty()) {
            ids.forEach(x -> ii.add(userrole.findById(x).orElseThrow()));
            ii.forEach(x -> {
                List<Role> bb = new ArrayList<Role>();
                bb = x.getRole();
                bb.stream().forEach(rr -> rr.getUserRoleProjet().remove(x));
                x.setRole(null);
                x.getUser().getList().remove(x);
                x.getP().getList().remove(x);
                x.setP(null);
                x.setUser(null);
                userrole.delete(x);
            });
        }
        UserRepo.delete(this.getUserById(id));

    }

    @Override
    public String generatePassword(int min, int max) {
        Random random = new Random();
        String up = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String low = "abcdefghijklmnopqrstuvwxyz";
        String numb = "0123456789";
        String specialChars = "@[]^_!.;(-){'}~$|%<>?=/&,";
        String all = up + low + numb + specialChars;
        List<Character> letterlist = new ArrayList<Character>();
        for (char c : all.toCharArray()) {
            letterlist.add(c);
        }
        Collections.shuffle(letterlist);
        String pass = "";
        for (int i = random.nextInt(max - min) + min; i > 0; --i) {
            pass += letterlist.get(random.nextInt(letterlist.size()));
        }
        return pass;
    }

    @Override
    public void updateResetPasswordToken(String token, String email) {
        Optional<User> user = Optional.ofNullable(UserRepo.findByEmail(email));
        if (user.get() != null) {
            user.get().setResetPasswordToken(token);
            UserRepo.save(user.get());
        } else {
            System.out.println("Could not find any user with the email " + email);
        }
    }

    @Override
    public Long getByResetPasswordToken(String token) {
        return UserRepo.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(newPassword);

            user.setPassword(encodedPassword);
            user.setResetPasswordToken(null);
            UserRepo.save(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void changepassword(Long id, String oldpassword, String newpassword) throws Exception {
        System.out.println(oldpassword + "old");
        System.out.println(newpassword + "new");
        User curent_user = this.getUserById(id);
        System.out.println(curent_user.getPassword());
        if (this.pass.matches(oldpassword, curent_user.getPassword())) {
            curent_user.setPassword(this.pass.encode(newpassword));

            System.out.println("password updated succesfully");
        }
    }

    @Override
    public void updatedetails(Long id, User user) throws Exception {
        User a = this.getUserById(id);
        a.setUsername(user.getUsername());
        a.setName(user.getName());
        a.setLastname(user.getLastname());
        a.setEmail(user.getEmail());

    }
}



