package com.example.paul.nfcsecondfactor0;

import android.util.Log;

import org.simpleframework.xml.core.Persister;

import java.io.File;

/**
 * Created by Paul on 1/02/2016.
 */
public class MockServer {

    Persister persister = new Persister();
    File file;

    /*
     * service to create/register a new user.
     */

    public Boolean registerUser(String userID, String email, String password, String nfcCardID) {

        Boolean userRegistered = false;
        //create new user, check if user already exists
        User newUser = new User(userID, password, nfcCardID, email);

        file = new File("StoredData/"+userID+".xml");
        //persistance for the user, exc exception thrown
        try {
            persister.write(newUser, file);
            userRegistered = true;
        } catch (Exception e) {
            Log.d("File Handling Error", e.toString());
        }
        return userRegistered;
    }

    public Boolean authenticateUser(String userID, String password, String nfcCardID) {

        file = new File("StoredData/"+userID+".xml");
        //get user, check if password matches
        User regUser = null;
        try {
            regUser = persister.read(User.class, file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (regUser.getPassword() == password && regUser.getNfcCardID() == nfcCardID) {
            return true;
        } else {

            return false;
        }
    }


}
