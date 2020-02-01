package com.katdmy.android.unocount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private final LayoutInflater mInflater;
    private List<Player> mPlayers;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onActiveClick(int code, boolean active);

        void onDeleteClick(Player player);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final Switch activeSwitch;
        private final TextView nameTextView;
        private final ImageView deleteImage;

        private PlayerViewHolder(View itemView, final OnItemClickListener listener, List<Player> players) {
            super(itemView);
            activeSwitch = itemView.findViewById(R.id.activeSwitch);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            deleteImage = itemView.findViewById(R.id.deleteImage);

            activeSwitch.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Player player = players.get(position);
                        listener.onActiveClick(player.getCode(), player.getActive());
                    }
                }
            });

            deleteImage.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                        listener.onDeleteClick(players.get(position));
                }
            });
        }
    }


    public PlayerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.player_item, parent, false);
        return new PlayerViewHolder(itemView, mListener, mPlayers);
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
