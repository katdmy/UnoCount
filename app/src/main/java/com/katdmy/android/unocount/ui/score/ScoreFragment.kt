package com.katdmy.android.unocount.ui.score

import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.katdmy.android.unocount.R
import com.katdmy.android.unocount.domain.score.model.Score
import com.katdmy.android.unocount.ui.widget.VerticalTextView
import kotlinx.coroutines.*
import java.util.*

class ScoreFragment : Fragment(R.layout.score_fragment) {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val scoreViewModel: ScoreViewModel by viewModels { ScoreViewModelFactory(requireActivity()) }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newGame = arguments?.getBoolean(NEW_GAME, true) ?: false
        if (newGame) {
            players = arguments?.getStringArray(PLAYERS_LIST) ?: emptyArray()
            scoreViewModel.deleteScore()
            scoreViewModel.insertPlayers(players)
        } else
            scoreViewModel.players.observe(viewLifecycleOwner, { loadedPlayers: String ->
                players = loadedPlayers.split(",").toTypedArray()
            })

        initViews()
        setRecyclerView()
        createPlayerViews()
        setButtons()
    }

    override fun onDestroyView() {
        recyclerView = null
        newRoundButton = null
        newRoundParent = null
        headerParent = null
        totalParent = null
        super.onDestroyView()
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

    private fun setButtons() {
        newRoundButton?.setOnClickListener {
            val currentScore: MutableList<Int> = ArrayList()

            for (i in players.indices) {
                val currentTextView = newRoundParent?.findViewWithTag<TextView>("newRound$i")
                if (TextUtils.isEmpty(currentTextView?.text)) {
                    currentScore.add(0)
                } else {
                    val currentPlayerScore = currentTextView?.text.toString().toInt()
                    currentScore.add(currentPlayerScore)
                }
            }
            scope.launch { scoreViewModel.insertScore(currentScore) }
            for (i in players.indices) {
                val currentTextView = newRoundParent?.findViewWithTag<TextView>("newRound$i")
                currentTextView?.text = ""
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
            playerTextView.setTypeface(Typeface.create("sans-serif-black", Typeface.NORMAL))
            headerParent?.addView(playerTextView)
            val loParams = playerTextView.layoutParams as LinearLayout.LayoutParams
            loParams.width = 0
            loParams.weight = 3f
            loParams.height = LinearLayout.LayoutParams.MATCH_PARENT
            playerTextView.layoutParams = loParams
        }
        for (i in players.indices) {
            val totalTextView = TextView(requireContext())
            totalTextView.setTypeface(Typeface.create("sans-serif-black", Typeface.NORMAL))
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
            newRoundEditText.setTypeface(Typeface.create("sans-serif-black", Typeface.NORMAL))
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