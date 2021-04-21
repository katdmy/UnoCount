package com.katdmy.android.unocount.data.db


object UnoCountContract {

    const val DATABASE_NAME = "uno_count.db"

    object PlayerEntry {

        const val TABLE_NAME = "player"

        //COLUMNS:
        const val COLUMN_NAME = "name"
        const val COLUMN_ACTIVE = "active"
    }

    object ScoreEntry {

        const val TABLE_NAME = "score"

        //COLUMNS:
        const val COLUMN_ROUND = "round"
        const val COLUMN_DATA = "data"
    }
}