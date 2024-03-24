package com.example.mehranm3;

import android.app.Application;

import com.example.mehranm3.database.AppDatabase;

import java.io.File;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        File dexOutputDir  = getCodeCacheDir();
        dexOutputDir.setReadOnly();
    }
}
