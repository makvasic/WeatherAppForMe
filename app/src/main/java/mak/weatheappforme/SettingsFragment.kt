package mak.weatheappforme

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val listPreference = findPreference<ListPreference>("theme_mode")
        listPreference?.entryValues = arrayOf(
            AppCompatDelegate.MODE_NIGHT_NO.toString(),
            AppCompatDelegate.MODE_NIGHT_YES.toString(),
            AppCompatDelegate.MODE_NIGHT_AUTO_TIME.toString()
        )

        listPreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, newValue ->

                when (preference.key) {
                    "theme_mode" -> AppCompatDelegate.setDefaultNightMode(newValue.toString().toInt())
                }
                true
            }
    }


}
