package mak.weatheappforme

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentCondition(

    @Json(name = "LocalObservationDateTime")
    val dateTime: String = "2019-06-04T15:00:00+02:00",
    @Json(name = "EpochTime")
    val epochDateTime: Long = 1559653200,
    @Json(name = "WeatherIcon")
    val weatherIcon: Int = 7,
    @Json(name = "WeatherText")
    val iconPhrase: String = "Cloudy",
    @Json(name = "IsDayTime")
    val daylight: Boolean = true,
    @Json(name = "Temperature")
    val temperature: Temperature = Temperature()
) {

    @JsonClass(generateAdapter = true)
    data class Temperature(
        @Json(name = "Metric") val metric: Metric = Metric()
    ) {
        @JsonClass(generateAdapter = true)
        data class Metric(
            @Json(name = "Value") val value: Float = 30f,
            @Json(name = "Unit") val unit: String = "C",
            @Json(name = "UnitType") val unitType: Int = 17
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