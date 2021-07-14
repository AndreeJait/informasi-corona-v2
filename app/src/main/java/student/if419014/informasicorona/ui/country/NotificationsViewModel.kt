package student.if419014.informasicorona.ui.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import student.if419014.informasicorona.model.response.GetCountriesAll

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    val countries:MutableLiveData<List<GetCountriesAll>> by lazy {
        MutableLiveData<List<GetCountriesAll>>()
    }
}