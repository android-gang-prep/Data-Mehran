package com.example.mehranm3.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "group")
public class GroupEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long user_id;
    private long group_id;

    public GroupEntity(long user_id, long group_id) {
        this.user_id = user_id;
        this.group_id = group_id;
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

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }
}
