package com.katdmy.android.unocount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class PlayerAdapter extends BaseAdapter {

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

    PlayerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    void setPlayers(List<Player> players) {
        mPlayers = players;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mPlayers != null)
            return mPlayers.size();
        else return 0;
    }

    @Override
    public Player getItem(int position) {
        return mPlayers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = mInflater.inflate(R.layout.player_item, parent, false);
        }

        Player player = getItem(position);

        Switch activeSwitch = itemView.findViewById(R.id.activeSwitch);
        TextView nameTextView = itemView.findViewById(R.id.nameTextView);
        ImageView deleteImage = itemView.findViewById(R.id.deleteImage);

        activeSwitch.setChecked(player.getActive());
        nameTextView.setText(player.getName());

        activeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (mCheckedListener != null)
                mCheckedListener.onCheckedChange(player.getName(), isChecked);
        });

        deleteImage.setOnClickListener(v -> {
            if (mClickListener != null)
                mClickListener.onDeleteClick(mPlayers.get(position));
        });

        return itemView;
    }
}
