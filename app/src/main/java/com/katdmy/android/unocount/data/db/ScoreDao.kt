package com.katdmy.android.unocount.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.katdmy.android.unocount.domain.score.model.Score

@Dao
interface ScoreDao {
    @get:Query("SELECT * FROM score WHERE round>0")
    val score: LiveData<List<Score>>

    @get:Query("SELECT data FROM score WHERE round=0")
    val players: LiveData<String>

    @Query("INSERT INTO score (round, data) VALUES (0, :data)")
    fun insertPlayers(data: String)

    @Query("INSERT INTO score (data) VALUES (:data)")
    fun insertScore(data: String)

    @Query("DELETE FROM score")
    fun deleteScore()
}