package com.katdmy.android.unocount;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.util.StringUtil;

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
    private List<Score> mScore;
    private int mPlayerCount;

    ScoreAdapter(Context context, int playerCount) {
        mInflater = LayoutInflater.from(context);
        mPlayerCount = playerCount;
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.score_item, parent, false);
        deleteStaticChildren(itemView);
        createItemView(itemView, mPlayerCount);
        return new ScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {
        if (mScore != null) {
            Score current = mScore.get(position);
            holder.roundTextView.setText(String.valueOf(current.getRound()));

            //1. Надо распарсить current.getScoreData в массив строк
            String scoreData = current.getScoreData();
            List<Integer> data = StringUtil.splitToIntList(scoreData);

            for (int i = 0; i < mPlayerCount; i++) {
                //2. Значения получившегося массива записать в соответствующие поля
                holder.scoreTextViews.get(i).setText(String.valueOf(data.get(i)));
            }
        }
    }

    void setScore(List<Score> score) {
        mScore = score;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mScore != null)
            return mScore.size();
        else return 0;
    }

    public void deleteStaticChildren(View parent) {
        LinearLayout scoreParent = parent.findViewById(R.id.scoreLayout);
        int count = scoreParent.getChildCount();
        for (int i = 1; i < count; i++) {
            TextView childView = (TextView) scoreParent.getChildAt(1);
            scoreParent.removeView(childView);
        }
    }

    public void createItemView(View parent, int playerCount) {
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
