package com.katdmy.android.unocount.data.repositories.player

import androidx.lifecycle.LiveData
import com.katdmy.android.unocount.data.db.PlayerDao
import com.katdmy.android.unocount.domain.player.PlayerRepository
import com.katdmy.android.unocount.domain.player.model.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class PlayerRepositoryImpl(
        private val playerDao: PlayerDao
) : PlayerRepository {
    private val scope = CoroutineScope(Dispatchers.IO)

    override val players: LiveData<List<Player>>
        get() = playerDao.players
    override val activePlayers: LiveData<List<String>>
        get() = playerDao.activePlayers

    override fun setActive(playerName: String, active: Boolean) {
        scope.launch { playerDao.setActive(playerName, active) }
    }

    override fun addPlayer(playerName: String) {
        scope.launch { if (playerDao.getPlayerByName(playerName).isEmpty()) playerDao.addPlayer(playerName) }
    }

    override fun deletePlayer(playerName: String) {
        scope.launch { playerDao.deletePlayer(playerName) }
    }
}