package com.example.mehranm3.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.mehranm3.database.entity.GroupEntity;
import com.example.mehranm3.database.entity.HistoryEntity;
import com.example.mehranm3.database.entity.UserModel;

import java.util.List;

public class GroupModel {
    @Embedded
    public GroupEntity group;

    @Relation(parentColumn = "user_id",
            entityColumn = "id")
    public UserModel user;


    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
