package com.katdmy.android.unocount;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private final LayoutInflater mInflater;
    protected List<Player> mPlayers;
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

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final Switch activeSwitch;
        private final TextView nameTextView;
        private String LOG_TAG = PlayerViewHolder.class.getSimpleName();

        private PlayerViewHolder(View itemView,
                                 final OnItemClickListener clickListener,
                                 final OnCheckedChangeListener checkedListener) {
            super(itemView);
            activeSwitch = itemView.findViewById(R.id.activeSwitch);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            ImageView deleteImage = itemView.findViewById(R.id.deleteImage);

            activeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (checkedListener != null) {
                    int position = PlayerViewHolder.this.getAdapterPosition();
                    Log.e(LOG_TAG, "Button is pressed to change player " + position + "(" + mPlayers.get(position) + ") active state.");
                    if (position != RecyclerView.NO_POSITION) {
                        Player player = mPlayers.get(position);
                        checkedListener.onCheckedChange(player.getName(), isChecked);
                    }
                }
            });

            deleteImage.setOnClickListener(v -> {
                if (clickListener != null) {
                    int position = getAdapterPosition();
                    Log.e(LOG_TAG, "Button is pressed to delete player " + position + "(" + mPlayers.get(position) + ").");
                    if (position != RecyclerView.NO_POSITION)
                        clickListener.onDeleteClick(mPlayers.get(position));
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
        return new PlayerViewHolder(itemView, mClickListener, mCheckedListener);
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
