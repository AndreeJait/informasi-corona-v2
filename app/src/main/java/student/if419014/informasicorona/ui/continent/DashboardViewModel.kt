package student.if419014.informasicorona.ui.continent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import student.if419014.informasicorona.model.response.GetAllResponse
import student.if419014.informasicorona.model.response.GetContinentsAll

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    val continents: MutableLiveData<List<GetContinentsAll>> by lazy {
        MutableLiveData<List<GetContinentsAll>>()
    }
}