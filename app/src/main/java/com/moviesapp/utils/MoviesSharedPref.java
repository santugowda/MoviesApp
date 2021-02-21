package com.moviesapp.utils;

import android.content.Context;

public class MoviesSharedPref extends SharedPrefsManager {

    private static MoviesSharedPref mInstance = null;
    private static final String MOVIES_CONFIG_PREFS = "AUTOLIST_CONFIG_PREFS";
    private static final String SEARCH_STRING = "SEARCH_STRING";


    public MoviesSharedPref(Context context) {
        super(context, MOVIES_CONFIG_PREFS);
    }

    public static synchronized MoviesSharedPref getInstance(Context context) {
        if(mInstance == null){
            mInstance = new MoviesSharedPref(context);
            mInstance.mContext = context.getApplicationContext();
        } else {
            if(mInstance.mContext == null)
                mInstance.mContext = context.getApplicationContext();
        }
        return mInstance;
    }

    public void setSearchedMovies(String movieSearched) {
        setStringPolicy(SEARCH_STRING, movieSearched);
    }

    public String getSearchedMovie() {
        return getStringPolicy(SEARCH_STRING, "Star");
    }
}
