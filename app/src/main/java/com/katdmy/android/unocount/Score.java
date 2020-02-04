package com.katdmy.android.unocount;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Score {
    @PrimaryKey
    @NonNull
    private int mRound;

    @NonNull
    private String mScoreData;

    public Score(int round, @NonNull String scoreData) {
        this.mRound = round;
        this.mScoreData = scoreData;
    }

    public int getRound() {
        return mRound;
    }

    public String getScoreData() {
        return mScoreData;
    }
}
