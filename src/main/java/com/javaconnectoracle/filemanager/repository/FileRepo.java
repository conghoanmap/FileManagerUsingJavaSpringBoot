package com.javaconnectoracle.filemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaconnectoracle.filemanager.entity.FileEntity;

@Repository
public interface FileRepo extends JpaRepository<FileEntity, String>{

}
