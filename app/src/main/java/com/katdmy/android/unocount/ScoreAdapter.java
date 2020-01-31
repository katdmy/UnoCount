package com.katdmy.android.unocount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    class ScoreViewHolder extends RecyclerView.ViewHolder {
        private final TextView roundTextView;
        private final TextView player1TextView;
        private final TextView player2TextView;
        private final TextView player3TextView;
        private final TextView player4TextView;
        private final TextView player5TextView;

        private ScoreViewHolder(View itemView) {
            super(itemView);
            roundTextView = itemView.findViewById(R.id.roundTextView);
            player1TextView = itemView.findViewById(R.id.player1TextView);
            player2TextView = itemView.findViewById(R.id.player2TextView);
            player3TextView = itemView.findViewById(R.id.player3TextView);
            player4TextView = itemView.findViewById(R.id.player4TextView);
            player5TextView = itemView.findViewById(R.id.player5TextView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Score> mScore;

    ScoreAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.score_item, parent, false);
        return new ScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {
        if (mScore != null) {
            Score current = mScore.get(position);
            holder.roundTextView.setText(String.valueOf(current.getRound()));
            holder.player1TextView.setText(String.valueOf(current.getPlayer1()));
            holder.player2TextView.setText(String.valueOf(current.getPlayer2()));
            holder.player3TextView.setText(String.valueOf(current.getPlayer3()));
            holder.player4TextView.setText(String.valueOf(current.getPlayer4()));
            holder.player5TextView.setText(String.valueOf(current.getPlayer5()));
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
}
