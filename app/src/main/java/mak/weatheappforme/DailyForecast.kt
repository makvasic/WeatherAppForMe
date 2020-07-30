package mak.weatheappforme

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyForecast(

    @Json(name = "Date")
    val dateTime: String = "2019-06-04T15:00:00+02:00",
    @Json(name = "EpochDate")
    val epochDateTime: Long = 1559653200,
    @Json(name = "Temperature")
    val temperature: Temperature = Temperature(),
    @Json(name = "Day")
    val day: Day = Day(),
    @Json(name = "Night")
    val night: Night = Night()
) {

    @JsonClass(generateAdapter = true)
    data class Temperature(
        @Json(name = "Minimum") val minimum: Minimum = Minimum(),
        @Json(name = "Maximum") val maximum: Maximum = Maximum()

    ) {
        @JsonClass(generateAdapter = true)
        data class Minimum(
            @Json(name = "Value") val value: Float = 30f,
            @Json(name = "Unit") val unit: String = "C",
            @Json(name = "UnitType") val unitType: Int = 17
        )

        @JsonClass(generateAdapter = true)
        data class Maximum(
            @Json(name = "Value") val value: Float = 30f,
            @Json(name = "Unit") val unit: String = "C",
            @Json(name = "UnitType") val unitType: Int = 17
        )
    }

    @JsonClass(generateAdapter = true)
    data class Day(
        @Json(name = "Icon") val icon: Int = 17
    )

    @JsonClass(generateAdapter = true)
    data class Night(
        @Json(name = "Icon") val icon: Int = 17
    )
}