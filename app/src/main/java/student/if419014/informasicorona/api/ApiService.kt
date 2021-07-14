package student.if419014.informasicorona.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import student.if419014.informasicorona.model.response.*

interface ApiService {
    @GET("v3/covid-19/all")
    fun getInfoGlobal(): Call<GetAllResponse>

    @GET("v3/covid-19/continents")
    fun getInfoContinent(): Call<List<GetContinentsAll>>

    @GET("v3/covid-19/countries")
    fun getInfoCountries(): Call<List<GetCountriesAll>>

    @GET("v3/covid-19/historical/all?lastdays=all")
    fun getHistory(): Call<HistoricalResponse>

    @GET("v3/covid-19/historical/{country}?lastdays=all")
    fun getCountryHistory(@Path("country") country:String): Call<CountryHistoricalResponse>
}