package com.katdmy.android.unocount;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ScoreViewModel extends AndroidViewModel {

    private ScoreRepository mRepository;
    private LiveData<ScoreMatrixCursor> mScoreMatrixCursor;
    private int mPlayerCount = 1;

    public ScoreViewModel(Application application) {
        super(application);
        mRepository = new ScoreRepository(mPlayerCount);
        mScoreMatrixCursor = mRepository.getCursor();
    }

    LiveData<ScoreMatrixCursor> getCursor() {
        return mScoreMatrixCursor;
    }

    void deleteAll() {
        mRepository.deleteAll();
    }

    void insert(List<Integer> currentScore) {
        mRepository.insert(currentScore);
    }

    void setPlayerCount(int playerCount) {
        mPlayerCount = playerCount;
        mRepository.setPlayerCount(playerCount);
    }
}
