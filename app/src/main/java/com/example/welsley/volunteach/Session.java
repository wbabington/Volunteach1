package com.example.welsley.volunteach;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Welsley on 8/7/2017.
 */

public class Session {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "volunteach";
    private static final String USER_LOGIN_SESSION = "isUserLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";

    public Session(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setLogin(String name, String email){
        editor.putBoolean(USER_LOGIN_SESSION, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public boolean checkLogin(){
        if(!this.Login()){
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

            return true;
        }
        return false;
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_NAME, preferences.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, preferences.getString(KEY_EMAIL, null));

        return user;
    }

    public boolean Login(){
        return preferences.getBoolean(USER_LOGIN_SESSION, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }
}
