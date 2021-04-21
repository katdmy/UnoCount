package com.katdmy.android.unocount.ui.player

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.katdmy.android.unocount.UnoCountApplication
import com.katdmy.android.unocount.data.repositories.player.PlayerRepositoryImpl

class PlayerViewModelFactory(
        private val activity: FragmentActivity,
        defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(activity, defaultArgs) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T = when (modelClass) {
        PlayerViewModel::class.java -> {
            PlayerViewModel(
                    PlayerRepositoryImpl(
                            (activity.application as UnoCountApplication).db.playerDao,
                    )
            )
        }
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}