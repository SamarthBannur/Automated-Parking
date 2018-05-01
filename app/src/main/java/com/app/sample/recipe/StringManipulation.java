package com.app.sample.recipe;

/**
 * Created by shashank on 17/4/18.
 */

class StringManipulation {



    public static String expandUsername(String username){
        return username.replace(".", " ");
    }

    public static String condenseUsername(String username){
        return username.replace(" " , ".");
    }


}
