package com.example.userservice.repo;

import com.example.userservice.domain.Risque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RisqueRepo extends JpaRepository<Risque,Long> {
    @Query(value = "select * from risque where projet_id In (select p_id from user_role_projet where user_id=?1)",nativeQuery = true)
    public List<Risque> findrisqueuser(Long id);
    @Query(value = "select*from risque where idprojet=?1",nativeQuery = true)
    public List<Long> findrisque(Long id);
    @Query(value="SELECT COUNT(*) from Risque r join r.processus_impacte u where u.id=?1 ")
    public Double testfindrisque(Long id);
    @Query (value="SELECT COUNT(*) from Risque",nativeQuery = true)
    public Double calcul();
    @Query (value="SELECT COUNT(*) from Risque where criticite < 4 ",nativeQuery = true)
    public Double calculAcceptable();
    @Query (value="SELECT COUNT(*) from Risque where criticite >=4 and criticite <=6",nativeQuery = true)
    public Double calculSurveiller();
    @Query (value="SELECT COUNT(*) from Risque where criticite >6",nativeQuery = true)
    public Double calculInacceptable();
    @Query(value = "select * from risque_aud  where id=?1 and probalite>0",nativeQuery = true)
    public List<?> tes(Long id);


}
