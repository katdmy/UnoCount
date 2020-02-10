package com.katdmy.android.unocount;

import android.database.MatrixCursor;

import java.util.ArrayList;
import java.util.List;

public class ScoreMatrixCursor extends MatrixCursor {
    private List<Integer> mTotal;

    ScoreMatrixCursor(String[] players) {
        super(players, 0);
        mTotal = new ArrayList<>();

        for (String ignored : super.getColumnNames()) {
            mTotal.add(0);
        }
    }

    @Override
    public void addRow(Iterable<?> columnValues) {
        super.addRow(columnValues);

        @SuppressWarnings("unchecked")
        ArrayList<Integer> list = (ArrayList<Integer>) columnValues;
        for (int i = 0; i < list.size(); i++) {
            mTotal.set(i, mTotal.get(i) + list.get(i));
        }
    }

    List<Integer> getTotal() {
        return mTotal;
    }
}
