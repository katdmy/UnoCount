package com.katdmy.android.unocount.domain.player.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.katdmy.android.unocount.data.db.UnoCountContract
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
        tableName = UnoCountContract.PlayerEntry.TABLE_NAME,
        indices = [Index(UnoCountContract.PlayerEntry.COLUMN_NAME)]
)
data class Player(
        @PrimaryKey
        @ColumnInfo(name = UnoCountContract.PlayerEntry.COLUMN_NAME)
        val name: String = "",
        @ColumnInfo(name = UnoCountContract.PlayerEntry.COLUMN_ACTIVE)
        var active: Boolean = true
) : Parcelable