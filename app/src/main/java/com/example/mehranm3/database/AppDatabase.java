package com.example.mehranm3.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.mehranm3.database.entity.GroupEntity;
import com.example.mehranm3.database.entity.GroupsEntity;
import com.example.mehranm3.database.entity.HistoryEntity;
import com.example.mehranm3.database.entity.LogEntity;
import com.example.mehranm3.database.entity.UserDongEntity;
import com.example.mehranm3.database.entity.UserModel;

@Database(entities = {LogEntity.class, UserModel.class, UserDongEntity.class, GroupEntity.class, GroupsEntity.class, HistoryEntity.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();

    public abstract GroupsDAO groupsDAO();

    public abstract HistoryDAO historyDAO();
    public abstract LogDAO logDAO();

    private static AppDatabase appDatabase;

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null)
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "mehran3").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        return appDatabase;
    }


}
