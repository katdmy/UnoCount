package com.katdmy.android.unocount.ui.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.katdmy.android.unocount.domain.score.ScoreRepository
import com.katdmy.android.unocount.domain.score.model.Score

class ScoreViewModel(
        private val scoreRepository: ScoreRepository
) : ViewModel() {

    var players: LiveData<Array<String>> = Transformations.distinctUntilChanged(scoreRepository.players)
    var score: LiveData<List<Score>> = scoreRepository.score
    var totalScore: LiveData<IntArray> = Transformations.switchMap(score) { score -> getTotalFromScore(score) }

    private fun getTotalFromScore(score: List<Score>): LiveData<IntArray> {
        val size = players.value?.size ?: 0
        val total = IntArray(size) { 0 }
        if (size > 0)
            for (roundScore in score) {
                val arrayOfScores = roundScore.data.split(",")
                for (i in players.value?.indices!!) {
                    total[i] = total[i] + Integer.parseInt(arrayOfScores[i])
                }
            }
        return MutableLiveData(total)
    }

    fun deleteScore() = scoreRepository.deleteScore()
    fun insertPlayers(playersList: Array<String>) = scoreRepository.insertPlayers(playersList)
    fun insertScore(currentScore: List<Int>) = scoreRepository.insertScore(currentScore)

    init {
        players = scoreRepository.players
        score = scoreRepository.score
    }
}