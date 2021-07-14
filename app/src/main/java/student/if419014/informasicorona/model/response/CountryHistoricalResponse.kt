package student.if419014.informasicorona.model.response

data class CountryHistoricalResponse(
    val country: String?=null,
    val province: List<String>?=null,
    val timeline: HistoricalResponse?=null
)
