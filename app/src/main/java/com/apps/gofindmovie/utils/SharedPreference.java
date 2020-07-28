package com.apps.gofindmovie.utils;

import android.content.Context;
import android.content.SharedPreferences;

/*
    Created by Andhika Putra Bagaskara - 10117167 - IF5
    on 27 may 2020
 */

public class SharedPreference {

    private static final String PREF_SESSION = "com.apps.andhika.gofindmovie.session";
    private static final String LAUNCH_STATUS = "LAUNCH_STATUS";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public SharedPreference() {
    }

    public static void setFirstTimeStatus(Boolean FirstLaunch, Context context){
        sharedPreferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        editor =sharedPreferences.edit();
        editor.putBoolean(LAUNCH_STATUS, FirstLaunch);
        editor.apply();
    }

    public static boolean getFirstTimeStatus(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(LAUNCH_STATUS, false);
    }

    public static void setLogOut(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove(LAUNCH_STATUS);
        editor.apply();
    }
}
