package com.katdmy.android.unocount;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class PlayerRepository {
    private PlayerDao mPlayerDao;

    PlayerRepository(Application application) {
        PlayerDatabase db = PlayerDatabase.getDatabase(application);
        mPlayerDao = db.playerDao();
    }

    LiveData<List<Player>> getPlayers() {
        return mPlayerDao.getPlayers();
    }

    LiveData<List<String>> getActivePlayers() {
        return mPlayerDao.getActivePlayers();
    }

    void setActive(int code, boolean active) {
        mPlayerDao.setActive(code, active);
    }

    void addPlayer(Player player) {
        mPlayerDao.addPlayer(player);
    }

    void deletePlayer(Player player) {
        mPlayerDao.deletePlayer(player);
    }
}
