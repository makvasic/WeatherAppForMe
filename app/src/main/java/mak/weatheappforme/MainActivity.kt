package mak.weatheappforme

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = HourlyForecastsAdapter()

        recyclerView.adapter = adapter

        mainViewModel.hourlyForecastsStateLiveData.observe(this, Observer {
            hourlyForecastProgressBar.visibility = View.GONE
            when (it) {
                is HourlyForecastsState.Loading -> hourlyForecastProgressBar.visibility = View.VISIBLE

                is HourlyForecastsState.Error -> Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                is HourlyForecastsState.Success -> adapter.submitList(it.hourlyForecastModels)
            }
        })

        mainViewModel.currentConditionStateLiveData.observe(this, Observer {
            currentConditionProgressBar.visibility = View.GONE
            when (it) {
                is CurrentConditionState.Loading -> currentConditionProgressBar.visibility = View.VISIBLE

                is CurrentConditionState.Error -> Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                is CurrentConditionState.Success -> {
                    temperatureTextView.text = it.currentConditionModel.temperature
                    weatherIconImageView.setImageResource(it.currentConditionModel.weatherIcon)
                    timeTextView.text = it.currentConditionModel.time
                }
            }
        })

        getForecast()

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

    private fun getForecast(cache: Boolean = true) {

        mainViewModel.getHourlyForecasts(cache)
        mainViewModel.getCurrentCondition(cache)
    }
}
