package com.example.mehranm3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.mehranm3.database.entity.GroupEntity;
import com.example.mehranm3.database.entity.GroupsEntity;
import com.example.mehranm3.database.entity.HistoryEntity;
import com.example.mehranm3.database.entity.UserDongEntity;
import com.example.mehranm3.models.GroupsModel;
import com.example.mehranm3.models.HistoryModel;

import java.util.List;

@Dao
public interface HistoryDAO {

    @Insert
    long addHistory(HistoryEntity history);

    @Update
    void updateHistory(HistoryEntity history);

    @Insert
    void addDong(UserDongEntity... dongs);

    @Query("DELETE FROM historyentity WHERE id=:historyID")
    void removeHistory(long historyID);

    @Query("DELETE FROM userdongentity WHERE history_id=:historyID")
    void removeAllDongs(long historyID);

    @Transaction
    @Query("SELECT * from historyentity where id=:id")
    LiveData<HistoryModel> getHistory(long id);
}
