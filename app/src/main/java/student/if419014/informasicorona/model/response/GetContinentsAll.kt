package student.if419014.informasicorona.model.response

import java.io.Serializable

data class GetContinentsAll(
    val updated: Long?= null,
    val cases: Long?=null,
    val todayCases:Long?=null,
    val deaths: Long?=null,
    val todayDeaths:Long?=null,
    val recovered:Long?=null,
    val todayRecovered:Long?=null,
    val active: Long?=null,
    val critical: Long?=null,
    val continent: String?=null
):Serializable
