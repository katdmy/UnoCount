package com.katdmy.android.unocount.domain.player

import androidx.lifecycle.LiveData
import com.katdmy.android.unocount.domain.player.model.Player

interface PlayerRepository {
    val players: LiveData<List<Player>>
    val activePlayers: LiveData<List<String>>

    fun setActive(playerName: String, active: Boolean)
    fun addPlayer(playerName: String)
    fun deletePlayer(playerName: String)
}