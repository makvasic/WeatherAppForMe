package mak.weatheappforme

import androidx.annotation.DrawableRes

data class CurrentConditionModel(

    val temperature: String,
    @DrawableRes val weatherIcon: Int,
    val time: String
)