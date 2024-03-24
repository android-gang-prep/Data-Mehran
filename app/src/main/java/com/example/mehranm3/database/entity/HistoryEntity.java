package com.example.mehranm3.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class HistoryEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private double group_id;
    private double total;
    private long time;
    private String unit;
    private long user_payed;

    public HistoryEntity(double group_id, double total, long time, String unit, long user_payed) {
        this.group_id = group_id;
        this.total = total;
        this.time = time;
        this.unit = unit;
        this.user_payed = user_payed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getGroup_id() {
        return group_id;
    }

    public void setGroup_id(double group_id) {
        this.group_id = group_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getUser_payed() {
        return user_payed;
    }

    public void setUser_payed(long user_payed) {
        this.user_payed = user_payed;
    }
}
