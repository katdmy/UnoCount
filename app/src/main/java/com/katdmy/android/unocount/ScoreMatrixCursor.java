package com.katdmy.android.unocount;

import android.database.MatrixCursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class ScoreMatrixCursor extends MatrixCursor {
    private MutableLiveData<List<Integer>> mTotal = new MutableLiveData<>();

    public ScoreMatrixCursor(String[] players) {
        super(players, 0);
        updateTotal();
    }

    @Override
    public void addRow(Object[] columnValues) {
        super.addRow(columnValues);
        updateTotal();
    }

    private void updateTotal() {
        List<Integer> total = new ArrayList<>();
        super.moveToFirst();

        for (String i : super.getColumnNames()) {
            total.add(0);
        }

        while (!super.isAfterLast()) {
            for (int i = 0; i < super.getCount(); i++) {
                total.set(i, total.get(i) + super.getInt(i));
            }
            super.moveToNext();
        }
        mTotal.setValue(total);
    }

    public LiveData<List<Integer>> getTotal() {
        return mTotal;
    }

    public List<Integer> getRound(int roundNumber) {
        List<Integer> round = new ArrayList<>();
        super.moveToPosition(roundNumber);

        for (int i = 0; i < super.getColumnCount(); i++)
            round.add(super.getInt(i));

        return round;
    }
}
