package com.katdmy.android.unocount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final Switch activeSwitch;
        private final TextView nameTextView;

        private PlayerViewHolder(View itemView) {
            super(itemView);
            activeSwitch = itemView.findViewById(R.id.activeSwitch);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Player> mPlayers;

    public PlayerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.player_item, parent, false);
        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        if (mPlayers != null) {
            Player player = mPlayers.get(position);
            holder.activeSwitch.setChecked(player.getActive());
            holder.nameTextView.setText(player.getName());
        }
    }

    void setPlayers(List<Player> players) {
        mPlayers = players;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mPlayers != null)
            return mPlayers.size();
        else return 0;
    }
}
