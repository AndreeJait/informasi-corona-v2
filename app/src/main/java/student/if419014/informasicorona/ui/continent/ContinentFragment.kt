package student.if419014.informasicorona.ui.continent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import pl.droidsonroids.gif.GifImageView
import ru.gildor.coroutines.retrofit.awaitResponse
import student.if419014.informasicorona.MainActivity
import student.if419014.informasicorona.R
import student.if419014.informasicorona.adapter.AdapterContinent
import student.if419014.informasicorona.api.ApiService
import student.if419014.informasicorona.api.Client
import student.if419014.informasicorona.model.response.GetContinentsAll

class ContinentFragment : Fragment() {
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var rvContinents : RecyclerView;
    private lateinit var etSearch: EditText
    private lateinit var loading: GifImageView
    var client: ApiService = Client().createService
    var job: Job?=null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_continent, container, false)
        rvContinents = root.findViewById(R.id.rvContinent)
        etSearch =root.findViewById(R.id.etSearch)
        loading = root.findViewById(R.id.loading)
        return root
    }

    override fun onStart() {
        super.onStart()
        val _activity = activity as MainActivity
        if(_activity.viewModel.continent.isNullOrEmpty()){
            loading.visibility = View.VISIBLE
            job = CoroutineScope(Dispatchers.IO).launch {
                val _countries = client.getInfoContinent().awaitResponse()
                if(_countries.isSuccessful){
                    withContext(Dispatchers.Main){
                        _activity.viewModel.continent = _countries.body()
                        dashboardViewModel.continents.value = _countries.body()
                        loading.visibility = View.GONE
                    }
                }
            }
        }else{
            dashboardViewModel.continents.value = _activity.viewModel.continent
            loading.visibility = View.GONE
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel.continents.observe(viewLifecycleOwner, {
            rvContinents.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
            val adapter = AdapterContinent(view.context, it)
            rvContinents.adapter = adapter
        })
        etSearch.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val _activity = activity as MainActivity
                var filter :List<GetContinentsAll> = _activity.viewModel.continent!!
                if(!s?.toString()?.trim().isNullOrEmpty()){
                    filter= _activity.viewModel.continent?.filter {
                        it.continent?.contains(s.toString(), true) == true
                    }!!
                }
                val adapter = filter.let { AdapterContinent(view.context, it) }
                rvContinents.adapter = adapter
            }

        })

    }

    override fun onStop() {
        super.onStop()
        loading.visibility = View.GONE
        if(job !== null){
            this.job!!.cancel()
        }
    }
}