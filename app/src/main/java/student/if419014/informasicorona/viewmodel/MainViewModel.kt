package student.if419014.informasicorona.viewmodel

import androidx.lifecycle.ViewModel
import student.if419014.informasicorona.model.response.GetAllResponse
import student.if419014.informasicorona.model.response.GetContinentsAll
import student.if419014.informasicorona.model.response.GetCountriesAll
import student.if419014.informasicorona.model.response.LineChartData

class MainViewModel:ViewModel() {
    var global:GetAllResponse ?=null
    var continent: List<GetContinentsAll>?= arrayListOf()
    var country: List<GetCountriesAll>?= arrayListOf()
    var barData:LineChartData?= null
}