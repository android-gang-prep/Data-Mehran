package com.example.mehranm3.ui.history;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mehranm3.database.AppDatabase;
import com.example.mehranm3.database.entity.GroupEntity;
import com.example.mehranm3.database.entity.GroupsEntity;
import com.example.mehranm3.database.entity.HistoryEntity;
import com.example.mehranm3.database.entity.LogEntity;
import com.example.mehranm3.database.entity.UserDongEntity;
import com.example.mehranm3.database.entity.UserModel;
import com.example.mehranm3.models.GroupsModel;
import com.example.mehranm3.models.HistoryModel;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AddHistoryViewModel extends AndroidViewModel {

    private LiveData<GroupsModel> group;
    private LiveData<HistoryModel> history;
    private AppDatabase appDatabase;

    public AddHistoryViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getAppDatabase(application);
    }

    public void init(long id) {
        group = appDatabase.groupsDAO().getGroup(id);
    }

    public void initHistory(long id) {
        history = appDatabase.historyDAO().getHistory(id);
    }

    public LiveData<GroupsModel> getGroup() {
        return group;
    }

    public LiveData<HistoryModel> getHistory() {
        return history;
    }

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void addHistory(HistoryEntity entity, List<UserDongEntity> entities) throws Exception {
        executorService.execute(() -> {
            long id = appDatabase.historyDAO().addHistory(entity);
            addLog(new LogEntity("History Added ID: " + id, System.currentTimeMillis()));
            addDongs(entities, id);
        });
    }

    public void updateHistory(HistoryEntity entity, List<UserDongEntity> entities) throws Exception {
        executorService.execute(() -> {
            appDatabase.historyDAO().removeAllDongs(entity.getId());
            appDatabase.historyDAO().updateHistory(entity);
            addLog(new LogEntity(" Update History ID: " + entity.getId(), System.currentTimeMillis()));

            addDongs(entities, entity.getId());
        });

    }

    public void addDongs(List<UserDongEntity> entities, long history) {
        UserDongEntity[] dongs = new UserDongEntity[entities.size()];

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).setHistory_id(history);
            dongs[i] = entities.get(i);
        }


        executorService.execute(() -> appDatabase.historyDAO().addDong(dongs));
    }

    public void removeHistory(HistoryModel dongs) {
        executorService.execute(() -> {
            addLog(new LogEntity(" History deleted ID: " + dongs.historyEntity.getId(), System.currentTimeMillis()));
            appDatabase.historyDAO().removeAllDongs(dongs.getHistoryEntity().getId());
            appDatabase.historyDAO().removeHistory(dongs.getHistoryEntity().getId());
        });
    }

    private void addLog(LogEntity... logEntities) {
        executorService.execute(() -> appDatabase.logDAO().insertAll(logEntities));
    }
}

