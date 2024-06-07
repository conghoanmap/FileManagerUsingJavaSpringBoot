package com.javaconnectoracle.filemanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERS")
public class UserEntity {

    @Id
    @Column(name = "USERNAME")
    private String USERNAME;
    @Column(name = "FULLNAME")
    private String FULLNAME;
    @Column(name = "PHONE")
    private String PHONE;
    @Column(name = "EMAIL")
    private String EMAIL;

    public UserEntity() {

    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String uSERNAME) {
        USERNAME = uSERNAME;
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public void setFULLNAME(String fULLNAME) {
        FULLNAME = fULLNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String eMAIL) {
        EMAIL = eMAIL;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String pHONE) {
        PHONE = pHONE;
    }

}
