package mak.weatheappforme

import com.squareup.moshi.Json

data class DailyForecast(

    @field:Json(name = "Date")
    val dateTime: String = "2019-06-04T15:00:00+02:00",
    @field:Json(name = "EpochDate")
    val epochDateTime: Long = 1559653200,
    @field:Json(name = "Temperature")
    val temperature: Temperature = Temperature(),
    @field:Json(name = "Day")
    val day: Day = Day(),
    @field:Json(name = "Night")
    val night: Night = Night()
) {

    data class Temperature(
        @field:Json(name = "Minimum") val minimum: Minimum = Minimum(),
        @field:Json(name = "Maximum") val maximum: Maximum = Maximum()

    ) {
        data class Minimum(
            @field:Json(name = "Value") val value: Float = 30f,
            @field:Json(name = "Unit") val unit: String = "C",
            @field:Json(name = "UnitType") val unitType: Int = 17
        )

        data class Maximum(
            @field:Json(name = "Value") val value: Float = 30f,
            @field:Json(name = "Unit") val unit: String = "C",
            @field:Json(name = "UnitType") val unitType: Int = 17
        )
    }

    data class Day(
        @field:Json(name = "Icon") val icon: Int = 17
    )

    data class Night(
        @field:Json(name = "Icon") val icon: Int = 17
    )
}