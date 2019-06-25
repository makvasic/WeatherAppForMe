package mak.weatheappforme

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class MainRepository(private val webApi: WebApi, private val locationId: String) {

    suspend fun getCurrentConditionState(): CurrentConditionState {
        val currentConditions = try {
            webApi.getCurrentCondition(locationId)
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

    suspend fun getHourlyForecastsState(): HourlyForecastsState {

        val hourlyForecasts = try {
            webApi.getHourlyForecasts(locationId)
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

    suspend fun getDailyForecastsState(): DailyForecastsState {

        val dailyForecastsWrapper = try {
            webApi.getDailyForecasts(locationId)
        } catch (e: Exception) {
            return when (e) {
                is ConnectException,
                is SocketTimeoutException,
                is UnknownHostException -> {
                    DailyForecastsState.Error("Check your internet connection")
                }
                is HttpException -> {
                    if (e.code() == 503) {
                        DailyForecastsState.Error("The allowed number of requests has been exceeded.")
                    } else {
                        DailyForecastsState.Error("#${e.code()} ${e.message()}")
                    }
                }
                else -> DailyForecastsState.Error("Unknown error")

            }
        }

        val dailyForecastModels = ArrayList<DailyForecastModel>()

        dailyForecastsWrapper.dailyForecasts.forEach {
            dailyForecastModels.add(
                DailyForecastModel(
                    "${it.temperature.minimum.value.roundToInt()}°/${it.temperature.maximum.value.roundToInt()}°",
                    getWeatherIcon(it.day.icon),
                    getWeatherIcon(it.night.icon),
                    getDate(it.epochDateTime)

                )
            )
        }

        return DailyForecastsState.Success(dailyForecastModels)
    }


    private fun getWeatherIcon(weatherIconId: Int) = when (weatherIconId) {
        1 -> R.drawable.weather_sunny
        2, 3, 4, 5, 6 -> R.drawable.weather_sunny_cloudy
        7, 8, 11 -> R.drawable.weather_cloudy
        12, 13, 14, 18, 26, 39, 40 -> R.drawable.weather_rain
        15, 16, 17, 41, 42 -> R.drawable.weather_storm
        19, 20, 21, 22, 23, 25, 43, 44 -> R.drawable.weather_snow
        33 -> R.drawable.weather_clear
        34, 35, 36, 37, 38 -> R.drawable.weather_clear_cloudy
        else -> R.mipmap.ic_launcher
    }

    private fun getDate(epoch: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd.MM.", Locale.getDefault())

        return simpleDateFormat.format(Date(epoch * 1000L))
    }

    private fun getTime(timestamp: String): String {

        val index = timestamp.indexOf('T') + 1
        return timestamp.substring(index, index + 5)

    }
}
