package com.javaconnectoracle.filemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaconnectoracle.filemanager.entity.FileTypeEntity;

@Repository
public interface FileTypeRepo extends JpaRepository<FileTypeEntity, String>{

}
