package com.katdmy.android.unocount

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.katdmy.android.unocount.ui.player.PlayerFragment
import com.katdmy.android.unocount.ui.score.ScoreFragment

class MainActivity : AppCompatActivity(), PlayerFragment.ShowScoreFragmentClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.PlayerTitle)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PlayerFragment.newInstance())
                    .commitNow()
        }
    }

    override fun showScoreFragment(newGame: Boolean, players: Array<String>?) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, ScoreFragment.newInstance(newGame, players))
                .addToBackStack(null)
                .commit()
    }


}