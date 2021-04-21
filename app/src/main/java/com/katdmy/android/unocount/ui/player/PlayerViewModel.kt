package com.katdmy.android.unocount.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.katdmy.android.unocount.domain.player.PlayerRepository
import com.katdmy.android.unocount.domain.player.model.Player

class PlayerViewModel(
        private val playerRepository: PlayerRepository
) : ViewModel() {
    var players: LiveData<List<Player>> = playerRepository.players
    var activePlayers: LiveData<List<String>> = playerRepository.activePlayers

    fun setActive(playerName: String, active: Boolean) {
        playerRepository.setActive(playerName, active)
    }

    fun insertPlayer(playerName: String) {
        playerRepository.addPlayer(playerName)
    }

    fun deletePlayer(playerName: String) {
        playerRepository.deletePlayer(playerName)
    }
}