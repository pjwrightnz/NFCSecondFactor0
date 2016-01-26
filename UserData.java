package com.example.paul.nfcsecondfactor0;

import java.util.HashMap;

/**
 * Created by Paul on 23/01/2016.
 */
public class UserData {

    public static HashMap userData = new HashMap<String, String>();

    public static void fillUserData() {
        userData.put("Paul","pass");
    }

    public static String checkUserData(String userName) {
        fillUserData();
        if (userData.containsKey(userName) )
            return (String) userData.get(userName);

        else {return null;}

    }


}
