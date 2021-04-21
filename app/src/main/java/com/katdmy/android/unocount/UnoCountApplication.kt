package com.katdmy.android.unocount

import android.app.Application
import com.katdmy.android.unocount.data.db.UnoCountDatabase

class UnoCountApplication : Application() {

    val db by lazy { UnoCountDatabase.create(this) }
}