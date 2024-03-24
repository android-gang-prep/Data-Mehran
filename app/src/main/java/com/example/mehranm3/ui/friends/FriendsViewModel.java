package com.example.mehranm3.ui.friends;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mehranm3.database.AppDatabase;
import com.example.mehranm3.database.entity.GroupEntity;
import com.example.mehranm3.database.entity.GroupsEntity;
import com.example.mehranm3.database.entity.LogEntity;
import com.example.mehranm3.database.entity.UserModel;
import com.example.mehranm3.models.GroupModel;
import com.example.mehranm3.models.GroupsModel;
import com.example.mehranm3.models.HistoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FriendsViewModel extends AndroidViewModel {

    private LiveData<List<UserModel>> users;
    private LiveData<GroupsModel> group;
    private AppDatabase appDatabase;

    public FriendsViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getAppDatabase(application);
        users = appDatabase.userDAO().getUsers();
    }

    public void init(long id) {
        group = appDatabase.groupsDAO().getGroup(id);
    }

    public LiveData<List<UserModel>> getUsers() {
        return users;
    }

    public LiveData<GroupsModel> getGroup() {
        return group;
    }

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public long addUser(UserModel userModel) {
        Callable<Long> longCallable = () -> appDatabase.userDAO().insert(userModel);
        long rowId = 0;

        Future<Long> future = executorService.submit(longCallable);
        try {
            rowId = future.get();
            addLog(new LogEntity(userModel.getName() + " Added to users", System.currentTimeMillis()));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rowId;
    }

    public void addGroup(GroupsEntity groupsEntity, List<UserModel> users) throws ExecutionException, InterruptedException {

        executorService.execute(() -> {
            addLog(new LogEntity(groupsEntity.getName() + " Added to groups", System.currentTimeMillis()));

            addMembers(users, appDatabase.groupsDAO().addGroup(groupsEntity),groupsEntity.getName());

        });


    }

    public void addMembersP(List<UserModel> users, long groupId) {
        executorService.execute(() -> {
            GroupEntity[] members = new GroupEntity[users.size()];

            for (int i = 0; i < users.size(); i++) {
                addLog(new LogEntity(users.get(i).getName() + " Added to group ID:"+groupId, System.currentTimeMillis()));

                members[i] = new GroupEntity(users.get(i).getId(), groupId);
            }
            appDatabase.groupsDAO().addMembers(members);
        });
    }


    private void addMembers(List<UserModel> users, long groupId,String name) {
        GroupEntity[] members = new GroupEntity[users.size()];

        for (int i = 0; i < users.size(); i++) {
            members[i] = new GroupEntity(users.get(i).getId(), groupId);
            addLog(new LogEntity(users.get(i).getName() + " Added to group: "+name, System.currentTimeMillis()));
        }

        appDatabase.groupsDAO().addMembers(members);
    }


    private void addLog(LogEntity... logEntities) {
        executorService.execute(() -> appDatabase.logDAO().insertAll(logEntities));
    }
}

