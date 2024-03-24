package com.example.mehranm3.ui.group;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mehranm3.database.AppDatabase;
import com.example.mehranm3.models.GroupModel;
import com.example.mehranm3.models.GroupsModel;

import java.util.List;

public class GroupViewModel extends AndroidViewModel {

    private LiveData<GroupsModel> group;
    private AppDatabase appDatabase;

    public GroupViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getAppDatabase(application);
    }

    public void init(long id) {
        group = appDatabase.groupsDAO().getGroup(id);
    }

    public LiveData<GroupsModel> getGroup() {
        return group;
    }


}