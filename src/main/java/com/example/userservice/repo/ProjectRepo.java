package com.example.userservice.repo;

import com.example.userservice.domain.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Projet,Long> {
    Projet findByName (String name );
    @Modifying
    @Query(value = "insert into projet (id,dateofcreation,description,name,nameofcreator) VALUES (:insertLink,null,null,:id,null)", nativeQuery = true)
    @Transactional
    void update(@Param("insertLink") Long insertLink, @Param("id") String id);
    @Query(value = "select*from risque where idprojet=?1",nativeQuery = true)
    public List<Long> findrisque(Long id);

}
