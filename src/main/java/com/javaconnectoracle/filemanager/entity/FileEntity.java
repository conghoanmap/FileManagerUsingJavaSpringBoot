package com.javaconnectoracle.filemanager.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "FILES")
public class FileEntity {
    @Id
    private String FILE_ID;
    private String FILE_NAME;
    private int FILE_SIZE;
    private String FILE_TYPE;
    private Date UPLOAD_DATE;
    private int PARENT_FOLDER;
    private String CLASS_ICON;
    private String FILE_PATH;

    public String getFILE_PATH() {
        return FILE_PATH;
    }

    public void setFILE_PATH(String fILE_PATH) {
        FILE_PATH = fILE_PATH;
    }

    public String getCLASS_ICON() {
        return CLASS_ICON;
    }

    public void setCLASS_ICON(String cLASS_ICON) {
        CLASS_ICON = cLASS_ICON;
    }

    public String getFILE_ID() {
        return FILE_ID;
    }

    public void setFILE_ID(String fILE_ID) {
        FILE_ID = fILE_ID;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String fILE_NAME) {
        FILE_NAME = fILE_NAME;
    }

    public int getFILE_SIZE() {
        return FILE_SIZE;
    }

    public void setFILE_SIZE(int fILE_SIZE) {
        FILE_SIZE = fILE_SIZE;
    }

    public String getFILE_TYPE() {
        return FILE_TYPE;
    }

    public void setFILE_TYPE(String fILE_TYPE) {
        FILE_TYPE = fILE_TYPE;
    }

    public Date getUPLOAD_DATE() {
        return UPLOAD_DATE;
    }

    public void setUPLOAD_DATE(Date uPLOAD_DATE) {
        UPLOAD_DATE = uPLOAD_DATE;
    }

    public int getPARENT_FOLDER() {
        return PARENT_FOLDER;
    }

    public void setPARENT_FOLDER(int pARENT_FOLDER) {
        PARENT_FOLDER = pARENT_FOLDER;
    }

}
