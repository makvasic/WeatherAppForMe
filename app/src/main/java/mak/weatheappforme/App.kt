package mak.weatheappforme

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val mode = sharedPreferences.getString(
            "theme_mode",
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM.toString()
        )?.toInt() ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

        AppCompatDelegate.setDefaultNightMode(mode)
    }
}