package com.katdmy.android.unocount;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlayerDao {
    @Query("SELECT * FROM player ORDER BY mCode")
    LiveData<List<Player>> getPlayers();

    @Insert
    void addPlayer(Player player);

    @Query("UPDATE player SET mActive=:active WHERE mCode=:code")
    void setActive(int code, boolean active);

    @Delete
    void deletePlayer(Player player);
}
