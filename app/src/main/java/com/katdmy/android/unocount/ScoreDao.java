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
            ":player1, " +
            ":player2, " +
            ":player3, " +
            ":player4," +
            ":player5)")
    void insertRound(int player1, int player2, int player3, int player4, int player5);

    @Query("DELETE FROM score")
    void deleteAll();

    @Query("SELECT MAX(mRound) AS mRound, " +
            "SUM(player1) AS player1, " +
            "SUM(player2) AS player2, " +
            "SUM(player3) AS player3, " +
            "SUM(player4) AS player4, " +
            "SUM(player5) AS player5 " +
            "FROM score")
    LiveData<Score> getTotal();

    @Query("SELECT mName FROM player WHERE mActive = 1 ORDER BY mCode")
    LiveData<List<String>> getActivePlayers();
}
