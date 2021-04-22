package com.katdmy.android.unocount.ui.score

import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.katdmy.android.unocount.R
import com.katdmy.android.unocount.domain.score.model.Score
import com.katdmy.android.unocount.ui.widget.VerticalTextView
import kotlinx.coroutines.*
import kotlin.collections.ArrayList


class ScoreFragment : Fragment(R.layout.score_fragment) {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val scoreViewModel: ScoreViewModel by activityViewModels { ScoreViewModelFactory(requireActivity()) }
    private var scoreAdapter: ScoreAdapter? = null
    private var players: Array<String> = emptyArray()
    private var recyclerView: RecyclerView? = null
    private var newRoundButton: ImageView? = null
    private var newRoundParent: LinearLayout? = null
    private var headerParent: LinearLayout? = null
    private var totalParent: LinearLayout? = null


    companion object {
        private const val NEW_GAME = "new_game"
        private const val PLAYERS_LIST = "players_list"

        fun newInstance(newGame: Boolean, players: Array<String>?) =
                ScoreFragment().apply {
                    arguments = Bundle()
                    arguments?.putBoolean(NEW_GAME, newGame)
                    if (newGame)
                        arguments?.putStringArray(PLAYERS_LIST, players)
                }
    }

    override fun onResume() {
        super.onResume()
        val newGame = arguments?.getBoolean(NEW_GAME, true) ?: false
        if (newGame) {
            players = arguments?.getStringArray(PLAYERS_LIST) ?: emptyArray()
            scoreViewModel.deleteScore()
            scoreViewModel.insertPlayers(players)

            clearPlayerViews()
            initViews()
            setRecyclerView()
            createPlayerViews()
            setButtons()
            setTotal()
        } else
            scoreViewModel.players.observe(viewLifecycleOwner, { loadedPlayers ->
                players = loadedPlayers
                clearPlayerViews()
                initViews()
                setRecyclerView()
                createPlayerViews()
                setButtons()
                setTotal()
            })
    }

    override fun onPause() {
        clearPlayerViews()
        recyclerView = null
        newRoundButton = null
        newRoundParent = null
        headerParent = null
        totalParent = null
        super.onPause()
    }

    private fun clearPlayerViews() {
        for (i in players.indices) {
            newRoundParent?.removeView(newRoundParent?.findViewWithTag<EditText>("newRound$i"))
            headerParent?.removeView(headerParent?.findViewWithTag<VerticalTextView>("header$i"))
            totalParent?.removeView(totalParent?.findViewWithTag<TextView>("total$i"))
        }
    }

    private fun initViews() {
        recyclerView = view?.findViewById(R.id.recyclerview)
        newRoundButton = view?.findViewById(R.id.newRoundButton)
        newRoundParent = view?.findViewById(R.id.newRoundLayout)
        headerParent = view?.findViewById(R.id.headerLayout)
        totalParent = view?.findViewById(R.id.totalLayout)
    }

    private fun setRecyclerView() {
        scoreAdapter = ScoreAdapter(requireContext(), players.size)
        recyclerView?.adapter = scoreAdapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        scoreViewModel.score.observe(viewLifecycleOwner, { score: List<Score> -> scoreAdapter!!.setScore(score) })
    }

    private fun setTotal() {
        scoreViewModel.totalScore.observe(viewLifecycleOwner, { totalScore: IntArray ->
            for (i in totalScore.indices) {
                val totalParent: LinearLayout? = view?.findViewById(R.id.totalLayout)
                val totalTextView = totalParent?.findViewWithTag<TextView>("total$i")
                totalTextView?.text = totalScore[i].toString()
            }
        })
    }

    private fun setButtons() {
        newRoundButton?.setOnClickListener {
            val currentScore: MutableList<Int> = ArrayList()

            for (i in players.indices) {
                val currentEditText = newRoundParent?.findViewWithTag<TextView>("newRound$i")
                if (TextUtils.isEmpty(currentEditText?.text)) {
                    currentScore.add(0)
                } else {
                    val currentPlayerScore = currentEditText?.text.toString().toInt()
                    currentScore.add(currentPlayerScore)
                }
            }
            scope.launch { scoreViewModel.insertScore(currentScore) }
            for (i in players.indices) {
                val currentEditText = newRoundParent?.findViewWithTag<EditText>("newRound$i")
                currentEditText?.text?.clear()
            }
            val current1TextView = newRoundParent?.findViewWithTag<TextView>("newRound0")
            current1TextView?.isFocusableInTouchMode = true
            current1TextView?.requestFocus()
        }
    }

    private fun createPlayerViews() {
        for (i in players.indices) {
            val name = players[i]
            val playerTextView = VerticalTextView(requireContext(), null)
            playerTextView.text = name
            playerTextView.tag = "header$i"
            playerTextView.typeface = Typeface.create("sans-serif-black", Typeface.NORMAL)
            headerParent?.addView(playerTextView)
            val loParams = playerTextView.layoutParams as LinearLayout.LayoutParams
            loParams.width = 0
            loParams.weight = 3f
            loParams.height = LinearLayout.LayoutParams.MATCH_PARENT
            playerTextView.layoutParams = loParams
        }
        for (i in players.indices) {
            val totalTextView = TextView(requireContext())
            totalTextView.typeface = Typeface.create("sans-serif-black", Typeface.NORMAL)
            totalTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            totalTextView.tag = "total$i"
            totalParent?.addView(totalTextView)
            val loParams = totalTextView.layoutParams as LinearLayout.LayoutParams
            loParams.width = 0
            loParams.weight = 3f
            loParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            totalTextView.layoutParams = loParams
        }
        for (i in players.indices) {
            val newRoundEditText = EditText(requireContext())
            newRoundEditText.typeface = Typeface.create("sans-serif-black", Typeface.NORMAL)
            newRoundEditText.inputType = InputType.TYPE_CLASS_NUMBER
            newRoundEditText.textAlignment = View.TEXT_ALIGNMENT_CENTER
            newRoundEditText.tag = "newRound$i"
            newRoundParent?.addView(newRoundEditText)
            val loParams = newRoundEditText.layoutParams as LinearLayout.LayoutParams
            loParams.width = 0
            loParams.weight = 3f
            loParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            newRoundEditText.layoutParams = loParams
        }
    }

}
