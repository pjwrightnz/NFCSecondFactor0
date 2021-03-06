package com.example.paul.nfcsecondfactor0;

import java.util.HashMap;

/**
 * Created by Paul on 23/01/2016.
 */
public class UserDataPersistance {

    //static hashmap for user data, string is userID, user has all user data
    public static HashMap<String, User> userData = new HashMap<String, User>();

    //checkUser Exists
    public boolean checkUserData(String userID) {
        if (userData.containsKey(userID)) {
            return true;
        } else {
            return false;
        }
    }
    // method to add user on registration
    public void addNewUser(User newUser) {
        userData.put(newUser.getUserID(), newUser);
    }

    //method to get existing user
    public User getUser(String userID) {
        User existingUser = userData.get(userID);
        return  existingUser;
    }


}
