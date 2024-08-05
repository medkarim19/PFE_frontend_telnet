package com.example.userservice.repo;

import com.example.userservice.domain.UserRoleProjet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface  UserRoleProjetRepo extends JpaRepository<UserRoleProjet,Long>{
    @Query(value="SELECT id FROM user_role_projet where p_id= ?1 and user_id= ?2",nativeQuery = true)
    Long find(Long id_projet1 ,Long iduseer );
    @Modifying
    @Query(value="DELETE FROM role_role WHERE userrole_id=:id",nativeQuery = true)
    void delete(@Param("id") Long id );
    @Modifying
    @Query(value = "insert into role_role (role_id,userrole_id) VALUES (:insertLink,:id)", nativeQuery = true)
    @Transactional
    void update(@Param("insertLink") Long insertLink, @Param("id") Long id);
    @Query(value="select id from user_role_projet where p_id=?1 and user_id=?2",nativeQuery = true)
    Long findAdmin(Long id_projet1 ,Long iduseer );
    @Query(value="select p_id from user_role_projet where user_id=?1",nativeQuery = true)
    List<Long> findprojet(Long iduseer );
    @Query(value="select id from user_role_projet where p_id=?1",nativeQuery = true)
    List<Long> findids(Long iduseer );
    @Query(value="select id from user_role_projet where user_id=?1",nativeQuery = true)
    List<Long> findids2(Long iduseer );
}
