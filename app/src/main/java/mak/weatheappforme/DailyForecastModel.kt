package mak.weatheappforme

import androidx.annotation.DrawableRes

data class DailyForecastModel(

    val temperature: String,
    @DrawableRes val dayIcon: Int,
    @DrawableRes val nightIcon: Int,
    val date: String
)