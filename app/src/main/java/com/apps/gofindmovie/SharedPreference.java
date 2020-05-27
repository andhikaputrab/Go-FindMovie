package com.apps.gofindmovie;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static final String PREF_SESSION = "com.apps.andhika.gofindmovie.session";
    private static final String LAUNCH_STATUS = "LAUNCH_STATUS";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public SharedPreference() {
    }

    static void setFirstTimeStatus(Boolean FirstLaunch, Context context){
        sharedPreferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        editor =sharedPreferences.edit();
        editor.putBoolean(LAUNCH_STATUS, FirstLaunch);
        editor.apply();
    }

    static boolean getFirstTimeStatus(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(LAUNCH_STATUS, false);
    }

    static void setLogOut(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove(LAUNCH_STATUS);
        editor.apply();
    }
}
