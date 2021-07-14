package student.if419014.informasicorona.ui.country

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import pl.droidsonroids.gif.GifImageView
import ru.gildor.coroutines.retrofit.awaitResponse
import student.if419014.informasicorona.MainActivity
import student.if419014.informasicorona.R
import student.if419014.informasicorona.adapter.AdapterContinent
import student.if419014.informasicorona.adapter.AdapterCountry
import student.if419014.informasicorona.api.Client
import student.if419014.informasicorona.model.response.GetContinentsAll
import student.if419014.informasicorona.model.response.GetCountriesAll

class CountryFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var rvCountry: RecyclerView
    var job:Job?=null
    val client = Client().createService
    lateinit var loading:GifImageView
    private lateinit var etSearch: EditText
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_country, container, false)
        rvCountry = root.findViewById(R.id.rvCountry)
        etSearch = root.findViewById(R.id.etSearch)
        loading = root.findViewById(R.id.loading)
        return root
    }

    override fun onStart() {
        super.onStart()
        val _active = activity as MainActivity
        if(_active.viewModel.country.isNullOrEmpty()){
            loading.visibility = View.VISIBLE
            job = CoroutineScope(Dispatchers.IO).launch {
                val _country = client.getInfoCountries().awaitResponse()
                if(_country.isSuccessful){
                    withContext(Dispatchers.Main){
                        _active.viewModel.country = _country.body()
                        notificationsViewModel.countries.value = _country.body()
                        loading.visibility = View.GONE
                    }
                }
            }
        }else{
            notificationsViewModel.countries.value = _active.viewModel.country
            loading.visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationsViewModel.countries.observe(viewLifecycleOwner, {
            rvCountry.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
            rvCountry.adapter = AdapterCountry(view.context, it)
        })
        etSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val _activity = activity as MainActivity
                var filter :List<GetCountriesAll> = _activity.viewModel.country!!
                if(!s?.toString()?.trim().isNullOrEmpty()){
                    filter= _activity.viewModel.country?.filter {
                        it.country?.contains(s.toString(), true) == true
                    }!!
                }
                val adapter = filter.let { AdapterCountry(view.context, it) }
                rvCountry.adapter = adapter
            }

        })
    }

    override fun onStop() {
        super.onStop()
        loading.visibility = View.GONE
        if(job !== null){
            job?.cancel()
        }
    }
}