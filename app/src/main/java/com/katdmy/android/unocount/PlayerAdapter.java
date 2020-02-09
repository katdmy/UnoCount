package com.katdmy.android.unocount;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private final LayoutInflater mInflater;
    private List<Player> mPlayers;
    private OnItemClickListener mClickListener;
    private OnCheckedChangeListener mCheckedListener;

    public interface OnItemClickListener {
        void onDeleteClick(Player player);
    }

    void setOnItemClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(String playerName, boolean isChecked);
    }

    void setOnCheckedChangeListener(OnCheckedChangeListener checkedListener) {
        mCheckedListener = checkedListener;
    }

    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final Switch activeSwitch;
        private final TextView nameTextView;
        private final ImageView deleteImage;
        private String LOG_TAG = PlayerViewHolder.class.getSimpleName();

        private PlayerViewHolder(View itemView,
                                 final OnItemClickListener clickListener,
                                 final OnCheckedChangeListener checkedListener,
                                 List<Player> players) {
            super(itemView);
            activeSwitch = itemView.findViewById(R.id.activeSwitch);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            deleteImage = itemView.findViewById(R.id.deleteImage);

            activeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (checkedListener != null) {
                    int position = PlayerViewHolder.this.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Player player = players.get(position);
                        checkedListener.onCheckedChange(player.getName(), isChecked);
                    }
                }
            });

            deleteImage.setOnClickListener(v -> {
                if (clickListener != null) {
                    int position = getAdapterPosition();
                    Log.e(LOG_TAG, "Button is pressed to delete player " + position + ".");
                    if (position != RecyclerView.NO_POSITION)
                        Log.e(LOG_TAG, "Start trying to delete player " + position + ".");
                        clickListener.onDeleteClick(players.get(position));
                    Log.e(LOG_TAG, "Start trying to delete player " + position + ".");
                }
            });
        }
    }


    PlayerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.player_item, parent, false);
        return new PlayerViewHolder(itemView, mClickListener, mCheckedListener, mPlayers);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
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
