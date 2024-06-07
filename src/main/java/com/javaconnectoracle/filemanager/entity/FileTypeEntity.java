package com.javaconnectoracle.filemanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FileTypeEntity {
    @Id
    private String TYPE_ID;
    private String TYPE_NAME;
    private String CLASS_ICON;
    
    public FileTypeEntity() {
    }
    
    public String getTYPE_ID() {
        return TYPE_ID;
    }
    public void setTYPE_ID(String tYPE_ID) {
        TYPE_ID = tYPE_ID;
    }
    public String getTYPE_NAME() {
        return TYPE_NAME;
    }
    public void setTYPE_NAME(String tYPE_NAME) {
        TYPE_NAME = tYPE_NAME;
    }
    public String getCLASS_ICON() {
        return CLASS_ICON;
    }
    public void setCLASS_ICON(String cLASS_ICON) {
        CLASS_ICON = cLASS_ICON;
    }
}
