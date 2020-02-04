package com.katdmy.android.unocount;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ScoreViewModel extends AndroidViewModel {

    private ScoreRepository mRepository;

    private LiveData<List<Score>> mScore;

    private LiveData<List<String>> mActivePlayers;

    public ScoreViewModel(Application application) {
        super(application);
        mRepository = new ScoreRepository(application);
        mScore = mRepository.getScore();
        mActivePlayers = mRepository.getActivePlayers();
    }

    LiveData<List<Score>> getScore() {
        return mScore;
    }

    void deleteAll() {
        mRepository.deleteAll();
    }

    public void insert(Score score) {
        mRepository.insert(score);
    }

    public LiveData<List<String>> getActivePlayers() {
        return mActivePlayers;
    }
}
