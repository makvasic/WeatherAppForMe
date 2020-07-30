package mak.weatheappforme

import retrofit2.http.GET
import retrofit2.http.Path

interface WebApi {

    @GET("currentconditions/v1/{locationId}")
    suspend fun getCurrentCondition(@Path("locationId") locationId: String): List<CurrentCondition>

    @GET("forecasts/v1/hourly/12hour/{locationId}?metric=true")
    suspend fun getHourlyForecasts(@Path("locationId") locationId: String): List<HourlyForecast>

    @GET("forecasts/v1/daily/5day/{locationId}?metric=true")
    suspend fun getDailyForecasts(@Path("locationId") locationId: String): DailyForecastsWrapper


    companion object {
        const val BELGRADE = 298198
        const val GYOR = 188380
        const val HERCEG_NOVI = 298280
        const val OHRID = 1172
        const val KRAGUJEVAC = 301638
    }
}