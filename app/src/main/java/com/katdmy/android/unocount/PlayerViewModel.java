package com.katdmy.android.unocount;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PlayerViewModel extends AndroidViewModel {

    private PlayerRepository mPlayerRepository;

    private LiveData<List<Player>> mPlayers;

    public PlayerViewModel(Application application) {
        super(application);
        mPlayerRepository = new PlayerRepository(application);
        mPlayers = mPlayerRepository.getPlayers();
    }

    public LiveData<List<Player>> getPlayers() {
        return mPlayers;
    }

    public void setActive(int code, boolean active) {
        mPlayerRepository.setActive(code, active);
    }

    public void insertPlayer(Player player) {
        mPlayerRepository.addPlayer(player);
    }

    public void deletePlayer(Player player) {
        mPlayerRepository.deletePlayer(player);
    }
}
