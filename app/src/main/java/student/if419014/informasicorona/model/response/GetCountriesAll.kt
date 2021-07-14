package student.if419014.informasicorona.model.response

import java.io.Serializable

data class GetCountriesAll(
    val updated: Long?= null,
    val cases: Long?=null,
    val todayCases:Long?=null,
    val deaths: Long?=null,
    val todayDeaths:Long?=null,
    val recovered:Long?=null,
    val todayRecovered:Long?=null,
    val active: Long?=null,
    val critical: Long?=null,
    val continent: String?=null,
    val population:Long?=null,
    val country: String?=null,
    val countryInfo:CountryInfo?=null
):Serializable
data class CountryInfo(
    val _id:Long?=null,
    val iso2:String?=null,
    val iso3:String?=null,
    val flag:String?=null,
    val lat:Double,
    val long:Double
):Serializable