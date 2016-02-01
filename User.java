package com.example.paul.nfcsecondfactor0;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Paul on 27/01/2016.
 */

@Root
public class User {

    @Element
    String userID;

    @Element
    String password;

    @Element
    String email;

    @Element
    String nfcCardID;

    public User(@Element(name="userID") String userID, @Element(name="Password")String password, @Element(name="email") String email, @Element(name="nfcCardID") String nfcCardID) {
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.nfcCardID = nfcCardID;
    }

    public String getNfcCardID() {
        return nfcCardID;
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
