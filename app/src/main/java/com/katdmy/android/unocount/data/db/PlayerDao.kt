package com.katdmy.android.unocount.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.katdmy.android.unocount.domain.player.model.Player

@Dao
interface PlayerDao {
    @get:Query("SELECT * FROM player")
    val players: LiveData<List<Player>>

    @Query("SELECT * FROM player WHERE name=:name")
    fun getPlayerByName(name: String): List<Player>

    @get:Query("SELECT name FROM player WHERE active = 1")
    val activePlayers: LiveData<List<String>>

    @Query("INSERT INTO player VALUES (:playerName, 1)")
    fun addPlayer(playerName: String)

    @Query("UPDATE player SET active=:active WHERE name=:playerName")
    fun setActive(playerName: String, active: Boolean)

    @Query("DELETE FROM player WHERE name=:playerName")
    fun deletePlayer(playerName: String)
}