package student.if419014.informasicorona.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import student.if419014.informasicorona.DetailActivity
import student.if419014.informasicorona.R
import student.if419014.informasicorona.model.response.GetContinentsAll
import java.text.DecimalFormat

class AdapterContinent(
    val context: Context,
    val continents: List<GetContinentsAll>
) :RecyclerView.Adapter<AdapterContinent.Holder>() {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, continent:GetContinentsAll){

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("continent", continent)
                itemView.context.startActivity(intent)
            }

            val decimalFormat = DecimalFormat("#,###")
            itemView.findViewById<TextView>(R.id.tvContinentName).text = continent.continent

            itemView.findViewById<TextView>(R.id.txtcases).text =
                "%s\n%s".format(itemView.resources.getString(R.string.cases),decimalFormat.format(continent.cases))
            itemView.findViewById<TextView>(R.id.txttodaycases).text =
                "%s\n%s".format(itemView.resources.getString(R.string.today_cases),decimalFormat.format(continent.todayCases))

            itemView.findViewById<TextView>(R.id.txtdeath).text =
                "%s\n%s".format(itemView.resources.getString(R.string.death),decimalFormat.format(continent.deaths))
            itemView.findViewById<TextView>(R.id.txttodaydeath).text =
                "%s\n%s".format(itemView.resources.getString(R.string.today_death),decimalFormat.format(continent.todayCases))

            itemView.findViewById<TextView>(R.id.txtrecovered).text =
                "%s\n%s".format(itemView.resources.getString(R.string.recovered),decimalFormat.format(continent.recovered))
            itemView.findViewById<TextView>(R.id.txttodayrecovered).text =
                "%s\n%s".format(itemView.resources.getString(R.string.today_recovered),decimalFormat.format(continent.todayRecovered))

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_continents, parent, false)
        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(position, continents[position])

    override fun getItemCount(): Int = continents.size

}