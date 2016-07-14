package com.target.z00195z.frameworktest;

/**
 * Created by z00195z on 7/13/16.
 */
import android.app.Activity;
import android.content.SharedPreferences;

public class PreviousSearch {
    SharedPreferences prefs;

    public PreviousSearch(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    String getSearchTerm(){
        return prefs.getString("SearchTerm", "shirts");
    }

    void setSearchTerm(String SearchTerm){
        prefs.edit().putString("SearchTerm", SearchTerm).commit();
    }
}
