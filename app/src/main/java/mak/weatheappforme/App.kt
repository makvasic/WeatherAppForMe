package mak.weatheappforme

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        var themeMode: String? = sharedPreferences.getString("theme_mode", null)

        if (themeMode == null) {
            themeMode = AppCompatDelegate.MODE_NIGHT_AUTO_TIME.toString()
            sharedPreferences.edit()
                .putString("theme_mode", themeMode)
                .apply()
        }

        AppCompatDelegate.setDefaultNightMode(themeMode.toInt())
    }
}