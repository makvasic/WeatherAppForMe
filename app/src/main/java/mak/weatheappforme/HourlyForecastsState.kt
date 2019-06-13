package mak.weatheappforme

sealed class HourlyForecastsState {
    object Loading : HourlyForecastsState()
    data class Success(val hourlyForecastModels: List<HourlyForecastModel>) : HourlyForecastsState()
    data class Error(val message: String) : HourlyForecastsState()
}