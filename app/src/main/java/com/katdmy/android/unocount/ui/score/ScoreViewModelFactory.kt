package com.katdmy.android.unocount.ui.score

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.katdmy.android.unocount.UnoCountApplication
import com.katdmy.android.unocount.data.repositories.score.ScoreRepositoryImpl

class ScoreViewModelFactory(
        private val activity: FragmentActivity,
        defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(activity, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
    ): T = when (modelClass) {
        ScoreViewModel::class.java -> {
            ScoreViewModel(
                    ScoreRepositoryImpl(
                            (activity.application as UnoCountApplication).db.scoreDao,
                    )
            )
        }
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}