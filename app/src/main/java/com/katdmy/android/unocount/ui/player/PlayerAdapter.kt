package com.katdmy.android.unocount.ui.player

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.katdmy.android.unocount.R
import com.katdmy.android.unocount.domain.player.model.Player

class PlayerAdapter(
        private val context: Context?,
        private val deleteClickListener: (playerName: String) -> Unit,
        private val checkedChangeListener: (playerName: String, isChecked: Boolean) -> Unit
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {
    private var players: List<Player> = emptyList()

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val activeSwitch = itemView.findViewById<SwitchMaterial>(R.id.activeSwitch)
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val deleteImage = itemView.findViewById<ImageView>(R.id.deleteImage)

        fun onBind(player: Player) {
            activeSwitch?.isChecked = player.active
            nameTextView?.text = player.name
            activeSwitch?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean -> checkedChangeListener(player.name, isChecked) }
            deleteImage?.setOnClickListener { deleteClickListener(player.name) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.player_item, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.onBind(players[position])
    }

    override fun getItemCount(): Int {
        return players.size
    }

    fun setPlayers(newPlayers: List<Player>) {
        players = newPlayers
        notifyDataSetChanged()
    }
}