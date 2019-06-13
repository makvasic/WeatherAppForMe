package mak.weatheappforme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val mainRepository by lazy { MainRepository(WebApiFactory.webApi) }

    private val _hourlyForecastsStateLiveData = MutableLiveData<HourlyForecastsState>()
    val hourlyForecastsStateLiveData: LiveData<HourlyForecastsState> = _hourlyForecastsStateLiveData

    private val _currentConditionStateLiveData = MutableLiveData<CurrentConditionState>()
    val currentConditionStateLiveData: LiveData<CurrentConditionState> = _currentConditionStateLiveData

    fun getHourlyForecasts(cache: Boolean) {
        if (_hourlyForecastsStateLiveData.value != null && cache) return

        _hourlyForecastsStateLiveData.value = HourlyForecastsState.Loading
        viewModelScope.launch {
            _hourlyForecastsStateLiveData.value = mainRepository.getHourlyForecastsState()
        }
    }

    fun getCurrentCondition(cache: Boolean) {
        if (_currentConditionStateLiveData.value != null && cache) return

        _currentConditionStateLiveData.value = CurrentConditionState.Loading
        viewModelScope.launch {
            _currentConditionStateLiveData.value = mainRepository.getCurrentConditionState()
        }
    }
}