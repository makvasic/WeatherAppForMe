package mak.weatheappforme

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.math.roundToInt

class MainRepository(private val webApi: WebApi) {

    suspend fun getHourlyForecastsState(): HourlyForecastsState {

        val hourlyForecasts = try {
            webApi.getHourlyForecasts()
        } catch (e: Exception) {
            return when (e) {
                is ConnectException,
                is SocketTimeoutException,
                is UnknownHostException -> {
                    HourlyForecastsState.Error("Check your internet connection")
                }
                is HttpException -> {
                    if (e.code() == 503) {
                        HourlyForecastsState.Error("The allowed number of requests has been exceeded.")
                    } else {
                        HourlyForecastsState.Error("#${e.code()} ${e.message()}")
                    }
                }
                else -> HourlyForecastsState.Error("Unknown error")

            }
        }

        val hourlyForecastModels = ArrayList<HourlyForecastModel>()

        hourlyForecasts.forEach {
            hourlyForecastModels.add(
                HourlyForecastModel(
                    "${it.temperature.value.roundToInt()}\u00B0",
                    getWeatherIcon(it.weatherIcon),
                    "${it.precipitationProbability}%",
                    getTime(it.dateTime)

                )
            )
        }

        return HourlyForecastsState.Success(hourlyForecastModels)
    }

    suspend fun getCurrentConditionState(): CurrentConditionState {
        val currentConditions = try {
            webApi.getCurrentCondition()
        } catch (e: Throwable) {
            return when (e) {
                is ConnectException,
                is SocketTimeoutException,
                is UnknownHostException -> {
                    CurrentConditionState.Error("Check your internet connection")
                }
                is HttpException -> {
                    if (e.code() == 503) {
                        CurrentConditionState.Error("The allowed number of requests has been exceeded.")
                    } else {
                        CurrentConditionState.Error("#${e.code()} ${e.message()}")
                    }
                }
                else -> CurrentConditionState.Error("Unknown error")
            }
        }

        val currentCondition = currentConditions[0]

        val currentConditionModel = CurrentConditionModel(
            "${currentCondition.temperature.metric.value.roundToInt()}\u00B0",
            getWeatherIcon(currentCondition.weatherIcon),
            getTime(currentCondition.dateTime)
        )

        return CurrentConditionState.Success(currentConditionModel)
    }


    private fun getWeatherIcon(weatherIconId: Int) = when (weatherIconId) {
        1 -> R.drawable.weather_sunny
        2, 3, 4, 5, 6 -> R.drawable.weather_sunny_cloudy
        7, 8, 11 -> R.drawable.weather_cloudy
        12, 13, 14, 18, 26, 39, 40 -> R.drawable.weather_rain
        15, 16, 17, 41, 42 -> R.drawable.weather_storm
        33 -> R.drawable.weather_clear
        34, 35, 36, 37, 38 -> R.drawable.weather_clear_cloudy
        else -> R.mipmap.ic_launcher
    }

    private fun getTime(timestamp: String): String {

        val index = timestamp.indexOf('T') + 1
        return timestamp.substring(index, index + 5)

    }
}
