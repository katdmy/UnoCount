package com.katdmy.android.unocount.ui.score

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.katdmy.android.unocount.R
import com.katdmy.android.unocount.domain.score.model.Score
import com.katdmy.android.unocount.ui.score.ScoreAdapter.ScoreViewHolder
import java.util.*

class ScoreAdapter(
        private val context: Context?,
        private val playerCount: Int
) : RecyclerView.Adapter<ScoreViewHolder>() {
    private var score: List<Score> = emptyList()

    inner class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val roundTextView: TextView = itemView.findViewById(R.id.roundTextView)
        val scoreTextViews: MutableList<TextView>

        init {
            scoreTextViews = ArrayList()
            for (i in 0 until playerCount) {
                val scoreTextView = itemView.findViewWithTag<TextView>("score$i")
                scoreTextViews.add(scoreTextView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.score_item, parent, false)
        createItemView(itemView, playerCount)
        return ScoreViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.roundTextView.text = (position + 1).toString()
        val scoreRound = score[position].data.split(",")
        for (i in 0 until playerCount) {
            holder.scoreTextViews[i].text = scoreRound[i]
        }
    }

    fun setScore(newScore: List<Score>) {
        score = newScore
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return score.size
    }

    private fun createItemView(parent: View, playerCount: Int) {
        val scoreParent = parent.findViewById<LinearLayout>(R.id.scoreLayout)
        for (i in 0 until playerCount) {
            val scoreTextView = TextView(parent.context)
            scoreTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            scoreTextView.tag = "score$i"
            scoreParent.addView(scoreTextView)
            val loParams = scoreTextView.layoutParams as LinearLayout.LayoutParams
            loParams.width = 0
            loParams.weight = 3f
            loParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            scoreTextView.layoutParams = loParams
        }
    }
}