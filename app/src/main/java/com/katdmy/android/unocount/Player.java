package com.katdmy.android.unocount;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Player {
    @PrimaryKey
    @NonNull
    private String mName;

    private boolean mActive;

    Player(@NonNull String name) {
        mName = name;
        mActive = true;
    }

    public String getName() {
        return mName;
    }

    boolean getActive() {
        return mActive;
    }

    void setActive(boolean active) {
        mActive = active;
    }
}
