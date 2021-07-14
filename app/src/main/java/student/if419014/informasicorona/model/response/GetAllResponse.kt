package student.if419014.informasicorona.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "response")
data class GetAllResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Long?=null,
    val updated: Long?= null,
    val cases: Long?=null,
    val todayCases:Long?=null,
    val deaths: Long?=null,
    val todayDeaths:Long?=null,
    val recovered:Long?=null,
    val todayRecovered:Long?=null,
    val active: Long?=null,
    val critical: Long?=null
)
