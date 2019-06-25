package mak.weatheappforme

import com.squareup.moshi.Json

class DailyForecastsWrapper(

    @field:Json(name = "DailyForecasts")
    val dailyForecasts: List<DailyForecast>

)