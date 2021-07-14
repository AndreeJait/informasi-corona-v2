package student.if419014.informasicorona.model.response

import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry

data class LineChartData(
    val cases: ArrayList<BarEntry>?= arrayListOf(),
    val death: ArrayList<BarEntry>?= arrayListOf(),
    val recovered: ArrayList<BarEntry>?= arrayListOf(),
    val date: ArrayList<String> = arrayListOf()
)
