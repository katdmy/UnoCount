package com.katdmy.android.unocount.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.katdmy.android.unocount.domain.player.model.Player
import com.katdmy.android.unocount.domain.score.model.Score

@Database(entities = [Player::class, Score::class], version = 2, exportSchema = false)
abstract class UnoCountDatabase : RoomDatabase() {
    abstract val playerDao: PlayerDao
    abstract val scoreDao: ScoreDao

    companion object {

        fun create(applicationContext: Context): UnoCountDatabase = Room.databaseBuilder(
                applicationContext,
                UnoCountDatabase::class.java,
                UnoCountContract.DATABASE_NAME
        )
                .fallbackToDestructiveMigration()
                .build()

    }
}