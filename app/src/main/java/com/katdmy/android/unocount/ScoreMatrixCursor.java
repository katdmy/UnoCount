package com.katdmy.android.unocount;

import android.database.MatrixCursor;

import java.util.ArrayList;
import java.util.List;

public class ScoreMatrixCursor extends MatrixCursor {
    private List<Integer> mTotal;

    public ScoreMatrixCursor(String[] players) {
        super(players, 0);
        mTotal = new ArrayList<>();

        for (String i : super.getColumnNames()) {
            mTotal.add(0);
        }
    }

    @Override
    public void addRow(Iterable<?> columnValues) {
        super.addRow(columnValues);

        ArrayList<Integer> list = (ArrayList<Integer>) columnValues;
        for (int i = 0; i < list.size(); i++) {
            mTotal.set(i, mTotal.get(i) + list.get(i));
        }
    }

    public List<Integer> getTotal() {
        return mTotal;
    }
}
