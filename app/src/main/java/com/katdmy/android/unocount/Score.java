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

    @ColumnInfo(name = "player1")
    private int mPlayer1;

    @ColumnInfo(name = "player2")
    private int mPlayer2;

    @ColumnInfo(name = "player3")
    private int mPlayer3;

    @ColumnInfo(name = "player4")
    private int mPlayer4;

    @ColumnInfo(name = "player5")
    private int mPlayer5;

    public Score(int round, int player1, int player2, int player3, int player4, int player5) {
        this.mRound = round;
        this.mPlayer1 = player1;
        this.mPlayer2 = player2;
        this.mPlayer3 = player3;
        this.mPlayer4 = player4;
        this.mPlayer5 = player5;
    }

    public int getRound() {
        return mRound;
    }

    public int getPlayer1() {
        return mPlayer1;
    }

    public int getPlayer2() {
        return mPlayer2;
    }

    public int getPlayer3() {
        return mPlayer3;
    }

    public int getPlayer4() {
        return mPlayer4;
    }

    public int getPlayer5() {
        return mPlayer5;
    }

    @Override
    public String toString() {
        return "Round " + mRound + ": (1)-" + mPlayer1 + ", (2)-" + mPlayer2 + ", (3)-" + mPlayer3 + ", (4)-" + mPlayer4 + ", (5)-" + mPlayer5 + ".";
    }
}
