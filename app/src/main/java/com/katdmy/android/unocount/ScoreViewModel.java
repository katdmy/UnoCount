package com.katdmy.android.unocount;

import android.app.Application;
import android.database.Cursor;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ScoreViewModel extends AndroidViewModel {

    private ScoreRepository mRepository;
    private LiveData<ScoreMatrixCursor> mScoreMatrixCursor;
    private LiveData<List<String>> mActivePlayers;

    public ScoreViewModel(Application application) {
        super(application);
        mRepository = new ScoreRepository(application);
        mScoreMatrixCursor = mRepository.getCursor();
        mActivePlayers = mRepository.getActivePlayers();
    }

    LiveData<ScoreMatrixCursor> getCursor() {
        return mScoreMatrixCursor;
    }

    void deleteAll() {
        mRepository.deleteAll();
    }

    public void insert(List<Integer> currentScore) {
        mRepository.insert(currentScore);
    }

    public LiveData<List<String>> getActivePlayers() {
        return mActivePlayers;
    }

}
