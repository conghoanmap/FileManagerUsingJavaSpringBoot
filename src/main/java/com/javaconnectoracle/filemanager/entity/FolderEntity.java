package com.javaconnectoracle.filemanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FolderEntity {
    @Id
    private int FOLDER_ID;
    private String FOLDER_NAME;
    private int PARENT_FOLDER;
    private String USERNAME;
    private String FOLDER_PATH;

    public String getFOLDER_PATH() {
        return FOLDER_PATH;
    }

    public void setFOLDER_PATH(String fOLDER_PATH) {
        FOLDER_PATH = fOLDER_PATH;
    }

    public FolderEntity() {
    }

    public int getFOLDER_ID() {
        return FOLDER_ID;
    }

    public void setFOLDER_ID(int fOLDER_ID) {
        FOLDER_ID = fOLDER_ID;
    }

    public String getFOLDER_NAME() {
        return FOLDER_NAME;
    }

    public void setFOLDER_NAME(String fOLDER_NAME) {
        FOLDER_NAME = fOLDER_NAME;
    }

    public int getPARENT_FOLDER() {
        return PARENT_FOLDER;
    }

    public void setPARENT_FOLDER(int pARENT_FOLDER) {
        PARENT_FOLDER = pARENT_FOLDER;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String uSERNAME) {
        USERNAME = uSERNAME;
    }

}
