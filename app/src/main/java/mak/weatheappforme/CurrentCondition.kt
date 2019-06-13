package mak.weatheappforme

import com.squareup.moshi.Json

data class CurrentCondition(

    @field:Json(name = "LocalObservationDateTime")
    val dateTime: String = "2019-06-04T15:00:00+02:00",
    @field:Json(name = "EpochTime")
    val epochDateTime: Long = 1559653200,
    @field:Json(name = "WeatherIcon")
    val weatherIcon: Int = 7,
    @field:Json(name = "WeatherText")
    val iconPhrase: String = "Cloudy",
    @field:Json(name = "IsDayTime")
    val daylight: Boolean = true,
    @field:Json(name = "Temperature")
    val temperature: Temperature = Temperature()
) {

    data class Temperature(
        @field:Json(name = "Metric") val metric: Metric = Metric()
    ) {
        data class Metric(
            @field:Json(name = "Value") val value: Float = 30f,
            @field:Json(name = "Unit") val unit: String = "C",
            @field:Json(name = "UnitType") val unitType: Int = 17
        )
    }
}

//{
//    "LocalObservationDateTime": "2019-06-11T15:51:00+02:00",
//    "EpochTime": 1560261060,
//    "WeatherText": "Light rain",
//    "WeatherIcon": 12,
//    "HasPrecipitation": true,
//    "PrecipitationType": "Rain",
//    "IsDayTime": true,
//    "Temperature": {
//    "Metric": {
//    "Value": 30,
//    "Unit": "C",
//    "UnitType": 17
//},
//    "Imperial": {
//    "Value": 86,
//    "Unit": "F",
//    "UnitType": 18
//}
//},
//    "MobileLink": "http://m.accuweather.com/en/rs/belgrade/298198/current-weather/298198?lang=en-us",
//    "Link": "http://www.accuweather.com/en/rs/belgrade/298198/current-weather/298198?lang=en-us"
//}