package com.example.mehranm3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mehranm3.database.entity.LogEntity;
import com.example.mehranm3.database.entity.UserModel;

import java.util.List;

@Dao
public interface LogDAO {
    @Insert
    void insertAll(LogEntity... logEntities);

    @Query("SELECT * from logs order by id desc")
    LiveData<List<LogEntity>> getLogs();


}
