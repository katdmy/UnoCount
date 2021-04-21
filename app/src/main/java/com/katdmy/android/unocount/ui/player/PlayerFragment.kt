package com.katdmy.android.unocount.ui.player

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.katdmy.android.unocount.R
import com.katdmy.android.unocount.domain.player.model.Player

class PlayerFragment : Fragment(R.layout.player_fragment) {

    private val LOG_TAG = this.javaClass.simpleName
    private val playerViewModel: PlayerViewModel by viewModels { PlayerViewModelFactory(requireActivity()) }
    private var adapter: PlayerAdapter? = null
    private var activePlayers: Array<String> = emptyArray()
    private var buttonNewGame: Button? = null
    private var buttonContinueGame: Button? = null
    private var listView: RecyclerView? = null
    private var buttonAdd: Button? = null
    private var showScoreClickListener: ShowScoreFragmentClickListener? = null

    companion object {
        fun newInstance() = PlayerFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setRecyclerView()
        setButtons()

        playerViewModel.players.observe(viewLifecycleOwner, { players: List<Player> -> adapter?.setPlayers(players) })
        playerViewModel.activePlayers.observe(viewLifecycleOwner, { players: List<String> ->
            activePlayers = players.toTypedArray()
            buttonNewGame?.isEnabled = activePlayers.isNotEmpty()
        })
    }

    override fun onDestroyView() {
        listView = null
        buttonAdd = null
        buttonNewGame = null
        buttonContinueGame = null
        super.onDestroyView()
    }


    private fun initViews() {
        listView = view?.findViewById(R.id.listView)
        buttonAdd = view?.findViewById(R.id.buttonAdd)
        buttonNewGame = view?.findViewById(R.id.buttonNewGame)
        buttonContinueGame = view?.findViewById(R.id.buttonContinueGame)
    }

    private fun setRecyclerView() {
        val deletePlayer = { playerName: String -> playerViewModel.deletePlayer(playerName) }
        val changePlayerActivity = { playerName: String, isChecked: Boolean -> playerViewModel.setActive(playerName, isChecked) }
        adapter = PlayerAdapter(requireContext(), deletePlayer, changePlayerActivity)
        listView?.layoutManager = LinearLayoutManager(requireContext())
        listView?.adapter = adapter
    }

    private fun setButtons() {
        buttonAdd?.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Введите имя нового игрока")
            val input = EditText(requireContext())
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            builder.setPositiveButton("Сохранить") { dialog: DialogInterface, _: Int ->
                val newName = input.text.toString()
                if (newName.isNotEmpty()) {
                    playerViewModel.insertPlayer(newName)
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("Отмена") { dialog: DialogInterface, _: Int -> dialog.cancel() }
            builder.show()
        }

        showScoreClickListener = context as? ShowScoreFragmentClickListener

        buttonNewGame?.setOnClickListener {
            showScoreClickListener?.showScoreFragment(true, activePlayers)
        }

        buttonContinueGame?.setOnClickListener {
            showScoreClickListener?.showScoreFragment(false)
        }
    }

    interface ShowScoreFragmentClickListener {
        fun showScoreFragment(newGame: Boolean, players: Array<String>? = null)
    }
}