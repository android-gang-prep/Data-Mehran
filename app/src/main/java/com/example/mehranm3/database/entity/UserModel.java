package com.example.mehranm3.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserModel {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    @Ignore
    private boolean selected;
    @Ignore
    private double dong;

    public UserModel(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public double getDong() {
        return dong;
    }

    public void setDong(double dong) {
        this.dong = dong;
    }
}
