package com.example.mehranm3.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.mehranm3.database.entity.HistoryEntity;
import com.example.mehranm3.database.entity.UserDongEntity;
import com.example.mehranm3.database.entity.UserModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryModel {
    @Embedded
    public HistoryEntity historyEntity;

    @Relation(
            parentColumn = "user_payed",
            entityColumn = "id"
    )
    public UserModel user_p;

    @Relation(
            parentColumn = "id",
            entityColumn = "history_id",
            entity = UserDongEntity.class
    )
    public List<UserDongModel> users;


    public HistoryEntity getHistoryEntity() {
        return historyEntity;
    }

    public UserModel getUser_payed() {
        return user_p;
    }

    public List<UserDongModel> getUsers() {
        return users;
    }

    public String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(historyEntity.getTime()));
    }

    public String getTotal() {
        return historyEntity.getTotal() + " " + historyEntity.getUnit();
    }
}
