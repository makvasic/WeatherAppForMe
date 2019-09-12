package mak.weatheappforme

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val themeListPreference = findPreference<ListPreference>("theme_mode")
        themeListPreference?.entryValues = arrayOf(
            AppCompatDelegate.MODE_NIGHT_NO.toString(),
            AppCompatDelegate.MODE_NIGHT_YES.toString(),
            AppCompatDelegate.MODE_NIGHT_AUTO_TIME.toString()
        )

        themeListPreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                AppCompatDelegate.setDefaultNightMode(newValue.toString().toInt())
                true
            }
    }

}
