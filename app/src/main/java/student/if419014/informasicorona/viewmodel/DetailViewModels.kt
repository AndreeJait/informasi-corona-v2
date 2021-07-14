package student.if419014.informasicorona.viewmodel

import androidx.lifecycle.ViewModel
import student.if419014.informasicorona.model.response.GetContinentsAll
import student.if419014.informasicorona.model.response.GetCountriesAll
import student.if419014.informasicorona.model.response.LineChartData

class DetailViewModels:ViewModel() {
    var containent: GetContinentsAll?=null
    var country: GetCountriesAll?=null
    var barData: LineChartData?= null
}