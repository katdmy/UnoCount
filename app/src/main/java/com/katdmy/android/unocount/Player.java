package com.katdmy.android.unocount;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Player {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mCode;

    private String mName;

    private boolean mActive;

    public Player(String name) {
        mCode = 0;
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

    public void setCode(int code) {
        mCode = code;
    }

    public void setActive(boolean active) {
        mActive = active;
    }
}
