package com.katdmy.android.unocount;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
interface ScoreDao {

    @Query("SELECT mName FROM player WHERE mActive = 1 ORDER BY mCode")
    LiveData<List<String>> getActivePlayers();

    @Query("SELECT COUNT(1) FROM player WHERE mActive = 1")
    int getPlayerCount();
}
