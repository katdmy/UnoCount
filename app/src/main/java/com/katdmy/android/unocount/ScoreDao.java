package com.katdmy.android.unocount;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
interface ScoreDao {
    @Query("SELECT * FROM score ORDER BY mRound ASC")
    LiveData<List<Score>> getAll();

    @Query("INSERT INTO score VALUES (" +
            "(SELECT MAX (mRound) FROM score)+1, " +
            ":scoreData)")
    void insertRound(String scoreData);

    @Query("DELETE FROM score")
    void deleteAll();

    @Query("SELECT mName FROM player WHERE mActive = 1 ORDER BY mCode")
    LiveData<List<String>> getActivePlayers();
}
