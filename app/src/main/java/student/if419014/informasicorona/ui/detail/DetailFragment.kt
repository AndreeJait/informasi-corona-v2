package student.if419014.informasicorona.ui.detail

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.*
import pl.droidsonroids.gif.GifImageView
import ru.gildor.coroutines.retrofit.awaitResponse
import student.if419014.informasicorona.DetailActivity
import student.if419014.informasicorona.MainActivity
import student.if419014.informasicorona.R
import student.if419014.informasicorona.api.ApiService
import student.if419014.informasicorona.api.Client
import student.if419014.informasicorona.model.response.AxisDateFormatter
import student.if419014.informasicorona.model.response.LineChartData
import student.if419014.informasicorona.repo.db.AppDatabase
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailFragment : Fragment() {
    val db by lazy { activity?.let { AppDatabase(it.applicationContext) } }
    val client : ApiService = Client().createService
    private lateinit var lineChart: BarChart
    private lateinit var loading: GifImageView
    var job: Job?=null
    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.detail_fragment, container, false)
        lineChart = root.findViewById(R.id.lineChart)
        loading = root.findViewById(R.id.loading)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        return root
    }

    override fun onStart() {
        super.onStart()
        val _activity = activity as DetailActivity
        if(_activity.viewModel.containent !== null){
            viewModel.continent.value = _activity.viewModel.containent
        }
        if(_activity.viewModel.country !== null){
            loading.visibility = View.VISIBLE
            if(_activity.viewModel.barData === null){
                job = CoroutineScope(Dispatchers.IO).launch {
                val linear_data = _activity.viewModel.country!!.country?.let {
                    client.getCountryHistory(
                        it
                    ).awaitResponse()
                }
                if (linear_data != null) {
                    if(linear_data.isSuccessful){
                        withContext(Dispatchers.Main){
                            viewModel.country.value = _activity.viewModel.country
                            val cases = ArrayList<BarEntry>()
                            val death = ArrayList<BarEntry>()
                            val recovered = ArrayList<BarEntry>()
                            var count = 0
                            var date = ArrayList<String>()
                            val dataHistory = linear_data.body()?.timeline
                            dataHistory?.cases?.keys?.forEach {
                                dataHistory.cases[it]?.let { it1 -> BarEntry(count.toFloat(), it1.toFloat()) }?.let { it2 -> cases.add(it2) }
                                dataHistory.deaths[it]?.let { it1 -> BarEntry(count.toFloat(), it1.toFloat()) }?.let { it2 -> death.add(it2) }
                                dataHistory.recovered[it]?.let { it1 -> BarEntry(count.toFloat(), it1.toFloat()) }?.let { it2 -> recovered.add(it2) }
                                date.add(it)
                                count += 1
                            }
                            viewModel.historyLine.value = LineChartData(cases, death, recovered, date)
                            (activity as DetailActivity).viewModel.barData = LineChartData(cases, death, recovered, date)
                            loading.visibility = View.GONE
                        }
                    }
                }
            }
            }else{
                viewModel.historyLine.value = _activity.viewModel.barData
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity?.intent?.hasExtra("continent") == true){
            lineChart.visibility = View.GONE
            viewModel.continent.observe(viewLifecycleOwner, {
                val decimalFormat = DecimalFormat("#,###")
                view.findViewById<TextView>(R.id.death).text = decimalFormat.format(it.deaths)
                view.findViewById<TextView>(R.id.todaydeath).text = decimalFormat.format(it.todayDeaths)
                view.findViewById<TextView>(R.id.recovered).text = decimalFormat.format(it.recovered)
                view.findViewById<TextView>(R.id.todayrecovered).text = decimalFormat.format(it.todayRecovered)
                view.findViewById<TextView>(R.id.cases).text = decimalFormat.format(it.cases)
                view.findViewById<TextView>(R.id.todaycases).text = decimalFormat.format(it.todayCases)
                view.findViewById<TextView>(R.id.active).text = decimalFormat.format(it.active)
                view.findViewById<TextView>(R.id.critical).text = decimalFormat.format(it.critical)
                val date = it.updated?.let { it1 -> Date(it1) }
                val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy - HH:mm a")
                view.findViewById<TextView>(R.id.tvUpdate).text = "Update " + simpleDateFormat.format(date)
            })
        }
        if(activity?.intent?.hasExtra("country") == true){
            lineChart.visibility = View.VISIBLE
            viewModel.historyLine?.observe(viewLifecycleOwner, {
                val kasusBarDataSet = BarDataSet(it.cases, "Kasus")
                kasusBarDataSet.color = resources.getColor(R.color.cases)

                val sembuhBarDataSet = BarDataSet(it.recovered, "Sembuh")
                sembuhBarDataSet.color = resources.getColor(R.color.recovered)

                val meninggalBarDataSet = BarDataSet(it.death, "Meninggal")
                meninggalBarDataSet.color = resources.getColor(R.color.death)

                val legend = lineChart.legend
//            legend.textColor = Color.WHITE
                legend.isEnabled = true
                legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
                legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                legend.orientation = Legend.LegendOrientation.HORIZONTAL
                legend.setDrawInside(false)

                lineChart.description.isEnabled = false
                lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
                lineChart.data = BarData(kasusBarDataSet, sembuhBarDataSet, meninggalBarDataSet)
                lineChart.animateXY(100, 500)

                val tanggal = AxisDateFormatter(it.date.toArray(arrayOfNulls<String>(it.date.size)))
                lineChart.xAxis?.valueFormatter = tanggal;
//            lineChart.axisLeft.textColor = Color.WHITE
//            lineChart.xAxis.textColor = Color.WHITE
                lineChart.axisRight.setDrawLabels(false)
                lineChart.data.setValueTextColor(Color.WHITE)

            })
            viewModel.country.observe(viewLifecycleOwner, {
                val decimalFormat = DecimalFormat("#,###")
                view.findViewById<TextView>(R.id.death).text = decimalFormat.format(it.deaths)
                view.findViewById<TextView>(R.id.todaydeath).text = decimalFormat.format(it.todayDeaths)
                view.findViewById<TextView>(R.id.recovered).text = decimalFormat.format(it.recovered)
                view.findViewById<TextView>(R.id.todayrecovered).text = decimalFormat.format(it.todayRecovered)
                view.findViewById<TextView>(R.id.cases).text = decimalFormat.format(it.cases)
                view.findViewById<TextView>(R.id.todaycases).text = decimalFormat.format(it.todayCases)
                view.findViewById<TextView>(R.id.active).text = decimalFormat.format(it.active)
                view.findViewById<TextView>(R.id.critical).text = decimalFormat.format(it.critical)
                val date = it.updated?.let { it1 -> Date(it1) }
                val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy - HH:mm a")
                view.findViewById<TextView>(R.id.tvUpdate).text = "Update " + simpleDateFormat.format(date)
            })
        }
    }

}