package com.example.mehranm3.ui.log;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mehranm3.database.AppDatabase;
import com.example.mehranm3.database.entity.LogEntity;
import com.example.mehranm3.models.GroupsModel;

import java.util.List;

public class LogsViewModel extends AndroidViewModel {

    private LiveData<List<LogEntity>> logs;
    private AppDatabase appDatabase;

    public LogsViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getAppDatabase(application);
        logs = appDatabase.logDAO().getLogs();
    }

    public LiveData<List<LogEntity>> getLogs() {
        return logs;
    }
}