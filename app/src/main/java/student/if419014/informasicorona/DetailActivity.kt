package student.if419014.informasicorona

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import student.if419014.informasicorona.model.response.GetContinentsAll
import student.if419014.informasicorona.model.response.GetCountriesAll
import student.if419014.informasicorona.ui.detail.DetailFragment
import student.if419014.informasicorona.viewmodel.DetailViewModels

class DetailActivity : AppCompatActivity() {
    lateinit var viewModel : DetailViewModels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewModel = ViewModelProvider(this).get(DetailViewModels::class.java)
        if(intent.hasExtra("continent")){
            val continent = intent.getSerializableExtra("continent") as GetContinentsAll
            viewModel.containent = continent
            supportActionBar?.title = "Detail " + continent.continent
        }
        if(intent.hasExtra("country")){
            val  country = intent.getSerializableExtra("country") as GetCountriesAll
            viewModel.country = country
            supportActionBar?.title = "Detail " + country.country
        }
        val detailFragment = DetailFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frameDetail, detailFragment)
        fragmentTransaction.commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}