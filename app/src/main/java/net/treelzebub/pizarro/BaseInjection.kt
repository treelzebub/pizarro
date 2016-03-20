package net.treelzebub.pizarro

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.Delegates

/**
 * Created by Tre Murillo on 3/19/16
 */
object BaseInjection {

    var context: Context            by Delegates.notNull()
    var prefs: SharedPreferences    by Delegates.notNull()
}