package com.example.paul.nfcsecondfactor0;

import java.io.IOException;

/**
 * Created by Paul on 1/02/2016.
 */
public class MockServer {

    UserDataPersistance udp = new UserDataPersistance();
    int PASSWORD_FAILED = 2;
    int NFC_FAILED = 1;
    int AUTHENTICATED = 0;

    /*
     * service to create/register a new user.
     */
    public Boolean registerUser(String userID, String password, String email, String nfcCardID) {
        //create new user
        User newUser = new User(userID, password, email, nfcCardID);
        //check if user already exists, return false else add user to the Map. Map used instead of
        //data persistance to keep app lite for proof of concept only.
        if (udp.checkUserData(userID)) {
            return false;
        } else {
            udp.addNewUser(newUser);
            return true;
        }
    }

    /*
     * service to authenticate a user.
     */
    public int authenticateUser(String userID, String password, String nfcCardID) {
        int returnValue = 3;
        if (udp.checkUserData(userID)) {
            returnValue = 2;
            if (udp.getUser(userID).getPassword().equals(password)) {
                returnValue = 1;
                if (udp.getUser(userID).getNfcCardID().equals(nfcCardID)) {
                    returnValue = 0;
                }
            }
        }
        return returnValue;
    }

    public void seedUserData() {
        registerUser("Test", "pass", "email", "0x6f936924");
    }
}
