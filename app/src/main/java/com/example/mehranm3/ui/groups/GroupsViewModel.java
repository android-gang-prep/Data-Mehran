package com.example.mehranm3.ui.groups;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mehranm3.App;
import com.example.mehranm3.database.AppDatabase;
import com.example.mehranm3.models.GroupsModel;

import java.util.List;

public class GroupsViewModel extends AndroidViewModel {

    private LiveData<List<GroupsModel>> groups;
    private AppDatabase appDatabase;

    public GroupsViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getAppDatabase(application);
        groups = appDatabase.groupsDAO().getGroups();
    }

    public LiveData<List<GroupsModel>> getGroups() {
        return groups;
    }


}