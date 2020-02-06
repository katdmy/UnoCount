package com.katdmy.android.unocount;

import java.util.List;

public class Score {

    private List<String> mActivePlayers;

    private ScoreMatrixCursor mScoreMatrixCursor;

    public Score(List<String> activePlayers, ScoreMatrixCursor scoreMatrixCursor) {
        this.mActivePlayers = activePlayers;
        this.mScoreMatrixCursor = scoreMatrixCursor;
    }

    public List<String> getActivePlayers() {
        return mActivePlayers;
    }

    public ScoreMatrixCursor getScoreMatrixCursor() {
        return mScoreMatrixCursor;
    }
}
