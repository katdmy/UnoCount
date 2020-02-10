package com.katdmy.android.unocount;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class PlayerViewModel extends AndroidViewModel {

    private PlayerRepository mPlayerRepository;

    private LiveData<List<Player>> mPlayers;

    private LiveData<List<String>> mActivePlayers;

    public PlayerViewModel(Application application) {
        super(application);
        mPlayerRepository = new PlayerRepository(application);
        mPlayers = mPlayerRepository.getPlayers();
        mActivePlayers = mPlayerRepository.getActivePlayers();
    }

    LiveData<List<Player>> getPlayers() {
        return mPlayers;
    }

    LiveData<List<String>> getActivePlayers() {
        return mActivePlayers;
    }

    void setActive(String playerName, boolean active) {
        mPlayerRepository.setActive(playerName, active);
    }

    void insertPlayer(Player player) {
        mPlayerRepository.addPlayer(player);
    }

    void deletePlayer(Player player) {
        mPlayerRepository.deletePlayer(player);
    }
}
