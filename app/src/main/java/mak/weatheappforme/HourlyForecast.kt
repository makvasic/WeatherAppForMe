package mak.weatheappforme

import com.squareup.moshi.Json

data class HourlyForecast(

    @field:Json(name = "DateTime")
    val dateTime: String = "2019-06-04T15:00:00+02:00",
    @field:Json(name = "EpochDateTime")
    val epochDateTime: Long = 1559653200,
    @field:Json(name = "WeatherIcon")
    val weatherIcon: Int = 7,
    @field:Json(name = "IconPhrase")
    val iconPhrase: String = "Cloudy",
    @field:Json(name = "HasPrecipitation")
    val precipitation: Boolean = false,
    @field:Json(name = "IsDaylight")
    val daylight: Boolean = true,
    @field:Json(name = "Temperature")
    val temperature: Temperature = Temperature(),
    @field:Json(name = "PrecipitationProbability")
    val precipitationProbability: Int = 50
) {

    data class Temperature(
        @field:Json(name = "Value") val value: Float = 30f,
        @field:Json(name = "Unit") val unit: String = "C",
        @field:Json(name = "UnitType") val unitType: Int = 17
    )
}

//{"DateTime": "2019-06-04T15:00:00+02:00",
//"EpochDateTime": 1559653200,
//"WeatherIcon": 7,
//"IconPhrase": "Cloudy",
//"HasPrecipitation": false,
//"IsDaylight": true,
//"Temperature": {
//    "Value": 23.3,
//    "Unit": "C",
//    "UnitType": 17
//},
//"PrecipitationProbability": 34,
//"MobileLink": "http://m.accuweather.com/en/rs/belgrade/298198/hourly-weather-forecast/298198?day=1&hbhhour=15&unit=c&lang=en-us",
//"Link": "http://www.accuweather.com/en/rs/belgrade/298198/hourly-weather-forecast/298198?day=1&hbhhour=15&unit=c&lang=en-us"
//}