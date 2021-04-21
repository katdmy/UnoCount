package com.katdmy.android.unocount.domain.score

import androidx.lifecycle.LiveData
import com.katdmy.android.unocount.domain.score.model.Score

interface ScoreRepository {
    val score: LiveData<List<Score>>
    val players: LiveData<String>

    fun insertPlayers(playersList: Array<String>)
    fun insertScore(currentScore: List<Int>)
    fun deleteScore()
}