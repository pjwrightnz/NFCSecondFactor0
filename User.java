package com.example.paul.nfcsecondfactor0;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Paul on 27/01/2016.
 */

@Root
public class User{

    String userID, password, email, nfcCardID;

    public User(String userID, String password, String email, String nfcCardID) {
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.nfcCardID = nfcCardID;
    }

    public String getNfcCardID() {
        return nfcCardID;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", userID='" + userID + '\'' +
                ", password='" + password + '\'' +
                ", nfcCardID='" + nfcCardID + '\'' +
                '}';
    }

    public void setNfcCardID(String nfcCardID) {
        this.nfcCardID = nfcCardID;
    }

    public String getUserID() {
        return userID;
    }

    public void setName(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





}
