package student.if419014.informasicorona.ui.global

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import student.if419014.informasicorona.model.response.GetAllResponse
import student.if419014.informasicorona.model.response.LineChartData

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    val respondent: MutableLiveData<GetAllResponse> by lazy {
        MutableLiveData<GetAllResponse>()
    }
    val historyLine: MutableLiveData<LineChartData> by lazy {
        MutableLiveData<LineChartData>()
    }
}