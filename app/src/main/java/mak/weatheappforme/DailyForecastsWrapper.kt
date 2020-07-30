package mak.weatheappforme

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DailyForecastsWrapper(

    @Json(name = "DailyForecasts")
    val dailyForecasts: List<DailyForecast>

)