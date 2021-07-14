package student.if419014.informasicorona.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HistoricalResponse(
        @SerializedName("cases")
        @Expose
        val cases:Map<String, Int>,
        @SerializedName("deaths")
        @Expose
        val deaths:Map<String, Int>,

        @SerializedName("recovered")
        @Expose
        val recovered:Map<String, Int>
)
