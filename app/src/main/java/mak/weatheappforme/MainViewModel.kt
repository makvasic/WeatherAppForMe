package mak.weatheappforme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var locationId = WebApi.BELGRADE.toString()

    private val mainRepository = MainRepository(WebApiFactory.webApi, locationId)

    private val _currentConditionStateLiveData = MutableLiveData<CurrentConditionState>()
    val currentConditionStateLiveData: LiveData<CurrentConditionState> = _currentConditionStateLiveData

    private val _hourlyForecastsStateLiveData = MutableLiveData<HourlyForecastsState>()
    val hourlyForecastsStateLiveData: LiveData<HourlyForecastsState> = _hourlyForecastsStateLiveData

    private val _dailyForecastsStateLiveData = MutableLiveData<DailyForecastsState>()
    val dailyForecastsStateLiveData: LiveData<DailyForecastsState> = _dailyForecastsStateLiveData

    fun getCurrentCondition(cache: Boolean) {
        if (_currentConditionStateLiveData.value != null && cache) return

        _currentConditionStateLiveData.value = CurrentConditionState.Loading
        viewModelScope.launch {
            _currentConditionStateLiveData.value = mainRepository.getCurrentConditionState()
        }
    }

    fun getHourlyForecasts(cache: Boolean) {
        if (_hourlyForecastsStateLiveData.value != null && cache) return

        _hourlyForecastsStateLiveData.value = HourlyForecastsState.Loading
        viewModelScope.launch {
            _hourlyForecastsStateLiveData.value = mainRepository.getHourlyForecastsState()
        }
    }

    fun getDailyForecasts(cache: Boolean) {
        if (_dailyForecastsStateLiveData.value != null && cache) return

        _dailyForecastsStateLiveData.value = DailyForecastsState.Loading
        viewModelScope.launch {
            _dailyForecastsStateLiveData.value = mainRepository.getDailyForecastsState()
        }
    }


}