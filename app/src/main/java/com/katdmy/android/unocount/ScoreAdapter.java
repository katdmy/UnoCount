package com.katdmy.android.unocount;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    class ScoreViewHolder extends RecyclerView.ViewHolder {
        private final TextView roundTextView;
        private final List<TextView> scoreTextViews;

        private ScoreViewHolder(View itemView) {
            super(itemView);
            roundTextView = itemView.findViewById(R.id.roundTextView);
            scoreTextViews = new ArrayList<>();
            for (int i = 0; i < mPlayerCount; i++) {
                TextView scoreTextView = itemView.findViewWithTag("score" + i);
                scoreTextViews.add(scoreTextView);
            }
        }
    }

    private final LayoutInflater mInflater;
    private Cursor mCursor;
    private int mPlayerCount;

    ScoreAdapter(Context context, int playerCount) {
        mInflater = LayoutInflater.from(context);
        mPlayerCount = playerCount;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.score_item, parent, false);
        createItemView(itemView, mPlayerCount);
        return new ScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        if (mCursor != null) {
            mCursor.moveToPosition(position);
            holder.roundTextView.setText(String.valueOf(position + 1));
            for (int i = 0; i < mPlayerCount; i++) {
                holder.scoreTextViews.get(i).setText(String.valueOf(mCursor.getInt(i)));
            }
        }
    }

    void setScore(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        else return 0;
    }

    private void createItemView(View parent, int playerCount) {
        LinearLayout scoreParent = parent.findViewById(R.id.scoreLayout);
        for (int i = 0; i < playerCount; i++) {
            TextView scoreTextView = new TextView(parent.getContext());
            scoreTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            scoreTextView.setTag("score" + i);
            scoreParent.addView(scoreTextView);

            LinearLayout.LayoutParams loParams = (LinearLayout.LayoutParams) scoreTextView.getLayoutParams();
            loParams.width = 0;
            loParams.weight = 3f;
            //loParams.height = parent.getHeight();
            loParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            scoreTextView.setLayoutParams(loParams);
        }
    }
}
