package com.katdmy.android.unocount;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PlayerRepository {
    private PlayerDao mPlayerDao;

    public PlayerRepository(Application application) {
        ScoreDatabase db = ScoreDatabase.getDatabase(application);
        mPlayerDao = db.playerDao();
    }

    public LiveData<List<Player>> getPlayers() {
        return mPlayerDao.getPlayers();
    }

    public void setActive(int code, boolean active) {
        mPlayerDao.setActive(code, active);
    }

    public void addPlayer(Player player) {
        mPlayerDao.addPlayer(player);
    }

    public void deletePlayer(Player player) {
        mPlayerDao.deletePlayer(player);
    }
}
