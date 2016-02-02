package com.example.paul.nfcsecondfactor0;

import android.util.Log;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import java.io.File;
import java.io.IOException;

/**
 * Created by Paul on 1/02/2016.
 */
public class MockServer {

    Persister persister = new Persister();
    File file;
    Serializer serializer;
    int PASSWORD_FAILED = 2;
    int NFC_FAILED = 1;
    int AUTHENTICATED = 0;

    /*
     * service to create/register a new user.
     */

    public Boolean registerUser(String userID, String email, String password, String nfcCardID) {

        Boolean userRegistered = false;
        //create new user, check if user already exists
        User newUser = new User(userID, password, nfcCardID, email);

        serializer = persister;
        file = new File(MainActivity.file + "_" + userID + ".xml");
        try {
            serializer.write(newUser,file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("TAG", file.getAbsolutePath());
        userRegistered = true;

        return userRegistered;
    }

    public int authenticateUser(String userID, String password, String nfcCardID) {

        int returnValue = 2;
        file = new File(MainActivity.file + "_" + userID + ".xml");
        if(file.exists()) {
            try {
                User regUser = null;
                regUser = persister.read(User.class, file);
                if(regUser.getPassword().equals(password)) {
                    returnValue=1;
                    if(regUser.getNfcCardID().equals(nfcCardID)) {
                        returnValue = 0;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnValue;
    }

    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {


    }
}
