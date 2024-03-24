package com.example.mehranm3.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.mehranm3.database.entity.UserDongEntity;
import com.example.mehranm3.database.entity.UserModel;

public class UserDongModel {
    @Embedded
    public UserDongEntity userDong;

    @Relation(
            parentColumn = "user_id",
            entityColumn = "id"
    )
    public UserModel user;

    public UserDongModel(UserDongEntity userDong, UserModel user) {
        this.userDong = userDong;
        this.user = user;
    }

    public UserDongEntity getUserDong() {
        return userDong;
    }

    public void setUserDong(UserDongEntity userDong) {
        this.userDong = userDong;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
