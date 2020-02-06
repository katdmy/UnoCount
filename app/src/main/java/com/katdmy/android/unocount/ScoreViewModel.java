package com.katdmy.android.unocount;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ScoreViewModel extends AndroidViewModel {

    private ScoreRepository mRepository;
    private LiveData<List<Score>> mScore;
    private LiveData<List<String>> mActivePlayers;
    private LiveData<List<Integer>> mTotal;

    public ScoreViewModel(Application application) {
        super(application);
        mRepository = new ScoreRepository(application);
        //mScore = mRepository.getScore();
        mActivePlayers = mRepository.getActivePlayers();
        mTotal = mRepository.getTotal();
    }

    LiveData<List<Score>> getScore() {
        return mScore;
    }

//    void deleteAll() {
//        mRepository.deleteAll();
//    }

    public void insert(List<Integer> currentScore) {
        mRepository.insert(currentScore);
    }

    public LiveData<List<String>> getActivePlayers() {
        return mActivePlayers;
    }

    public LiveData<List<Integer>> getTotal() {
        return mTotal;
    }
}
