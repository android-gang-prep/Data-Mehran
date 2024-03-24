package com.example.mehranm3.models;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.example.mehranm3.database.entity.GroupEntity;
import com.example.mehranm3.database.entity.GroupsEntity;
import com.example.mehranm3.database.entity.HistoryEntity;
import com.example.mehranm3.database.entity.UserModel;

import java.util.ArrayList;
import java.util.List;

public class GroupsModel {
    @Embedded
    public GroupsEntity group;

    @Relation(parentColumn = "id",
            entityColumn = "group_id",
            entity = GroupEntity.class)
    public List<GroupModel> groupModel;

    @Ignore
    private List<UserModel> users;

    @Relation(parentColumn = "id",
            entityColumn = "group_id",
            entity = HistoryEntity.class)
    public List<HistoryModel> history;

    public GroupsModel(GroupsEntity group, List<GroupModel> groupModel, List<HistoryModel> history) {
        this.group = group;
        this.groupModel = groupModel;
        this.history = history;
        users = new ArrayList<>();

        for (int i = 0; i < groupModel.size(); i++) {
            users.add(groupModel.get(i).getUser());
        }
    }

    public GroupsEntity getGroup() {
        return group;
    }

    public List<GroupModel> getGroupModel() {
        return groupModel;
    }

    public List<HistoryModel> getHistory() {
        return history;
    }

    public List<UserModel> getUsers() {
        return users;
    }
}
