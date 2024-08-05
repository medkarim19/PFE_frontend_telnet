package com.example.userservice.repo;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.userservice.service.FileDB;

public interface FileDBRepository extends JpaRepository <FileDB, String> {

}
