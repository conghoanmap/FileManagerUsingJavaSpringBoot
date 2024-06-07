package com.javaconnectoracle.filemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaconnectoracle.filemanager.entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, String>{

}
