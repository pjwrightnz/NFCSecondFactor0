package com.example.paul.nfcsecondfactor0;

/**
 * Created by Paul on 1/02/2016.
 */
public class MockServer {

    /*
     * service to create/register a new user.


     */

    public void registerUser(String userID, String email, String password, String nfcCardID) {

       //create new user, check if user already exists
        User newUser = new User(userID, password, nfcCardID, email);

        //persistance for the user
        //TODO

    }

    public Boolean authenticateUser(String userID, String password, String nfcCardID) {

        //get user, check if password matches
        User regUser = get user from persistance

        if (regUser.getPassword() == password && regUser.getnfcCardID == nfcCardID) {
            return true;
        }else {

            return true;
        }
    }




}
