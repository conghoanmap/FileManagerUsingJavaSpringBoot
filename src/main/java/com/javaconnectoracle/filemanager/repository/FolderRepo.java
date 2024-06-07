package com.javaconnectoracle.filemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaconnectoracle.filemanager.entity.FolderEntity;

@Repository
public interface FolderRepo extends JpaRepository<FolderEntity, Integer>{

}
