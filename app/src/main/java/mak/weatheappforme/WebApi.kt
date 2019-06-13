package mak.weatheappforme

import retrofit2.http.GET

interface WebApi {

    @GET("forecasts/v1/hourly/12hour/$BELGRADE?metric=true")
    suspend fun getHourlyForecasts(): List<HourlyForecast>


    @GET("currentconditions/v1/$BELGRADE")
    suspend fun getCurrentCondition(): List<CurrentCondition>

    companion object {
        const val BELGRADE = 298198
    }
}