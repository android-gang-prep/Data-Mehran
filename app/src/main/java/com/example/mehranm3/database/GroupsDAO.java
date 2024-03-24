package com.example.mehranm3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.mehranm3.database.entity.GroupEntity;
import com.example.mehranm3.database.entity.GroupsEntity;
import com.example.mehranm3.models.GroupModel;
import com.example.mehranm3.models.GroupsModel;

import java.util.List;

@Dao
public interface GroupsDAO {

    @Insert
    long addGroup(GroupsEntity group);


    @Insert
    void addMembers(GroupEntity... group);


    @Transaction
    @Query("SELECT * from groups order by id desc")
    LiveData<List<GroupsModel>> getGroups();


    @Transaction
    @Query("SELECT * from groups order by id desc")
    List<GroupsModel> getGroups2();

    @Transaction
    @Query("SELECT * from groups where id=:id")
    LiveData<GroupsModel> getGroup(long id);



    /*@Transaction
    @Query("SELECT * from `group` where group_id=:id")
    LiveData<GroupModel> getGroup(long id);

    @Query("SELECT * from `group` where group_id=:id")
    List<GroupEntity> getGroup2(long id);*/
}
