package com.katdmy.android.unocount.ui.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.katdmy.android.unocount.domain.score.ScoreRepository
import com.katdmy.android.unocount.domain.score.model.Score

class ScoreViewModel(
        private val scoreRepository: ScoreRepository
) : ViewModel() {

    val score: LiveData<List<Score>> = scoreRepository.score
    val players: LiveData<String> = scoreRepository.players

    fun deleteScore() = scoreRepository.deleteScore()
    fun insertPlayers(playersList: Array<String>) = scoreRepository.insertPlayers(playersList)
    fun insertScore(currentScore: List<Int>) = scoreRepository.insertScore(currentScore)

}