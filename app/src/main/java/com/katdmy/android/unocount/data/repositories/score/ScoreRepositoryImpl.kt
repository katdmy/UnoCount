package com.katdmy.android.unocount.data.repositories.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.katdmy.android.unocount.data.db.ScoreDao
import com.katdmy.android.unocount.domain.score.ScoreRepository
import com.katdmy.android.unocount.domain.score.model.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


internal class ScoreRepositoryImpl(
        private val scoreDao: ScoreDao
) : ScoreRepository {
    private val LOG_TAG = this::class.java.simpleName
    private val scope = CoroutineScope(Dispatchers.IO)

    override val score: LiveData<List<Score>>
        get() = scoreDao.score

    override val players: LiveData<Array<String>>
        get() = Transformations.switchMap(scoreDao.players) { loadedPlayers -> MutableLiveData(loadedPlayers.split(",").toTypedArray()) }


    override fun insertPlayers(playersList: Array<String>) {
        scope.launch { scoreDao.insertPlayers(playersList.joinToString(",")) }
    }

    override fun insertScore(currentScore: List<Int>) {
        scope.launch { scoreDao.insertScore(currentScore.joinToString(",")) }
    }

    override fun deleteScore() {
        scope.launch { scoreDao.deleteScore() }
    }
}