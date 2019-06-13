package mak.weatheappforme

import androidx.annotation.DrawableRes

data class HourlyForecastModel(

    val temperature: String,
    @DrawableRes val weatherIcon: Int,
    val precipitationProbability: String,
    val time: String
)