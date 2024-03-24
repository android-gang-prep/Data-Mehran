package com.example.mehranm3.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserDongEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long user_id;
    private long history_id;
    private double dong;

    public UserDongEntity(long user_id, long history_id, double dong) {
        this.user_id = user_id;
        this.history_id = history_id;
        this.dong = dong;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getHistory_id() {
        return history_id;
    }

    public void setHistory_id(long history_id) {
        this.history_id = history_id;
    }

    public double getDong() {
        return dong;
    }

    public void setDong(double dong) {
        this.dong = dong;
    }
}
