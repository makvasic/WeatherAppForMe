package mak.weatheappforme

sealed class DailyForecastsState {
    object Loading : DailyForecastsState()
    data class Success(val dailyForecastModels: List<DailyForecastModel>) : DailyForecastsState()
    data class Error(val message: String) : DailyForecastsState()
}