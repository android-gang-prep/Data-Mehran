package com.example.mehranm3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mehranm3.database.entity.UserModel;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insertAll(UserModel... users);

    @Insert
    long insert(UserModel user);

    @Update
    void updateAll(UserModel... users);

    @Update
    void update(UserModel user);


    @Query("SELECT * from users order by id desc")
    LiveData<List<UserModel>> getUsers();



    @Query("SELECT * from users where id=:id limit 1")
    UserModel getUser(long id);

}
