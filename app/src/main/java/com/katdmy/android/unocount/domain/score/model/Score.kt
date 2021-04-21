package com.katdmy.android.unocount.domain.score.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.katdmy.android.unocount.data.db.UnoCountContract

@Entity(
        tableName = UnoCountContract.ScoreEntry.TABLE_NAME,
        indices = [Index(UnoCountContract.ScoreEntry.COLUMN_ROUND)]
)
data class Score(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = UnoCountContract.ScoreEntry.COLUMN_ROUND)
        val round: Int = 0,
        @ColumnInfo(name = UnoCountContract.ScoreEntry.COLUMN_DATA)
        var data: String = ""
)