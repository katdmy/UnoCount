package com.katdmy.android.unocount;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PlayerDao {
    @Query("SELECT * FROM player")
    LiveData<List<Player>> getPlayers();

    @Query("SELECT * FROM player WHERE mName=:name")
    List<Player> getPlayerByName(String name);

    @Query("SELECT mName FROM player WHERE mActive = 1")
    LiveData<List<String>> getActivePlayers();

    @Insert
    void addPlayer(Player player);

    @Query("UPDATE player SET mActive=:active WHERE mName=:playerName")
    void setActive(String playerName, boolean active);

    @Delete
    void deletePlayer(Player player);
}
