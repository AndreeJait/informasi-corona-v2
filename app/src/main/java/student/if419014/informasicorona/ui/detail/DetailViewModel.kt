package student.if419014.informasicorona.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import student.if419014.informasicorona.model.response.GetContinentsAll
import student.if419014.informasicorona.model.response.GetCountriesAll
import student.if419014.informasicorona.model.response.LineChartData

class DetailViewModel : ViewModel() {
    val continent: MutableLiveData<GetContinentsAll> by lazy {
        MutableLiveData<GetContinentsAll>()
    }
    val country: MutableLiveData<GetCountriesAll> by lazy {
        MutableLiveData<GetCountriesAll>()
    }
    val historyLine: MutableLiveData<LineChartData> by lazy {
        MutableLiveData<LineChartData>()
    }
}