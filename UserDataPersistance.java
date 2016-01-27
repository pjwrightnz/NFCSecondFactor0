package com.example.paul.nfcsecondfactor0;

import java.util.HashMap;

/**
 * Created by Paul on 23/01/2016.
 */
public class UserDataPersistance {

    //static hashmap for user data, string is userID, user has all user data
    public static HashMap<String, User> userData = new HashMap<String, User>();

    public static void fillUserData() {
        User testUser = new User("Paul", "pass", "email");
        userData.put(testUser.getUserID(), testUser);
    }

    public static boolean checkUserData(String userID) {
        if (userData.containsKey(userID)) {
            return true;
        } else {
            return false;
        }
    }

    public void addNewUser(User newUser) {
        userData.put(newUser.getUserID(), newUser);
    }


}
