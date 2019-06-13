package mak.weatheappforme

sealed class CurrentConditionState {
    object Loading : CurrentConditionState()
    data class Success(val currentConditionModel: CurrentConditionModel) : CurrentConditionState()
    data class Error(val message: String) : CurrentConditionState()
}