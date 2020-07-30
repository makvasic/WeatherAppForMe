package mak.weatheappforme

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val mainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.registerOnSharedPreferenceChangeListener(this)

        MainViewModel.locationId =
            preferences.getString("location_id", WebApi.BELGRADE.toString())!!

        MainViewModel.locationName = getLocationNameFromId(MainViewModel.locationId)

        supportActionBar?.title = MainViewModel.locationName


        if (preferences.getBoolean("weather_hourly", true)) hourlyRecyclerView.visibility =
            View.VISIBLE
        else hourlyRecyclerView.visibility = View.GONE

        if (preferences.getBoolean("weather_daily", true)) dailyRecyclerView.visibility =
            View.VISIBLE
        else dailyRecyclerView.visibility = View.GONE

        val hourlyAdapter = HourlyForecastsAdapter()
        val dailyAdapter = DailyForecastsAdapter()

        hourlyRecyclerView.adapter = hourlyAdapter
        dailyRecyclerView.adapter = dailyAdapter

        mainViewModel.currentConditionStateLiveData.observe(this, Observer {
            // currentConditionProgressBar.visibility = View.GONE
            when (it) {
//                is CurrentConditionState.Loading -> currentConditionProgressBar.visibility = View.VISIBLE

                is CurrentConditionState.Error -> Toast.makeText(
                    this,
                    it.message,
                    Toast.LENGTH_LONG
                ).show()

                is CurrentConditionState.Success -> {
                    temperatureTextView.text = it.currentConditionModel.temperature
                    weatherIconImageView.setImageResource(it.currentConditionModel.weatherIcon)
                    timeTextView.text = it.currentConditionModel.time
                }
            }
        })

        mainViewModel.hourlyForecastsStateLiveData.observe(this, Observer {
            //            hourlyForecastProgressBar.visibility = View.GONE
            when (it) {
//                is HourlyForecastsState.Loading -> hourlyForecastProgressBar.visibility = View.VISIBLE

                is HourlyForecastsState.Error -> Toast.makeText(
                    this,
                    it.message,
                    Toast.LENGTH_LONG
                ).show()

                is HourlyForecastsState.Success -> hourlyAdapter.submitList(it.hourlyForecastModels)
            }
        })

        mainViewModel.dailyForecastsStateLiveData.observe(this, Observer {
            //            dailyForecastProgressBar.visibility = View.GONE
            when (it) {
//                is DailyForecastsState.Loading -> dailyForecastProgressBar.visibility = View.VISIBLE

                is DailyForecastsState.Error -> Toast.makeText(
                    this,
                    it.message,
                    Toast.LENGTH_LONG
                ).show()

                is DailyForecastsState.Success -> dailyAdapter.submitList(it.dailyForecastModels)
            }
        })

        getForecast()

    }

    private fun getLocationNameFromId(locationId: String): String {
        val locationIds = resources.getStringArray(R.array.location_values)
        val locationNames = resources.getStringArray(R.array.location_entries)

        locationIds.forEachIndexed { index, id ->
            if (id == locationId) {
                return locationNames[index]
            }
        }
        return locationNames[0]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_menu_refresh -> getForecast(false)

            R.id.main_menu_settings -> SettingsActivity.start(this)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        preferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences, key: String) {
        when (key) {
            "weather_hourly" ->
                if (preferences.getBoolean(key, true)) {
                    hourlyRecyclerView.visibility = View.VISIBLE
                    mainViewModel.getHourlyForecasts(false)
                } else hourlyRecyclerView.visibility = View.GONE

            "weather_daily" ->
                if (preferences.getBoolean(key, true)) {
                    dailyRecyclerView.visibility = View.VISIBLE
                    mainViewModel.getDailyForecasts(false)

                } else dailyRecyclerView.visibility = View.GONE

            "location_id" -> {
                MainViewModel.locationId = preferences.getString(key, WebApi.BELGRADE.toString())!!
                MainViewModel.locationName = getLocationNameFromId(MainViewModel.locationId)
                getForecast(false)
            }

        }
    }

    private fun getForecast(cache: Boolean = true) {

        supportActionBar?.title = MainViewModel.locationName

        mainViewModel.getCurrentCondition(cache)
        if (preferences.getBoolean("weather_hourly", true)) {
            mainViewModel.getHourlyForecasts(cache)
        }
        if (preferences.getBoolean("weather_daily", true)) {
            mainViewModel.getDailyForecasts(cache)
        }
    }
}
