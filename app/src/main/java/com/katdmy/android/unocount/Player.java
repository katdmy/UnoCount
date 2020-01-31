package com.katdmy.android.unocount;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Player {
    @PrimaryKey
    @NonNull
    private int mCode;

    private String mName;

    private boolean mActive;

    public Player(int code, String name) {
        mCode = code;
        mName = name;
        mActive = true;
    }

    public int getCode() {
        return mCode;
    }

    public String getName() {
        return mName;
    }

    public boolean getActive() {
        return mActive;
    }
}
