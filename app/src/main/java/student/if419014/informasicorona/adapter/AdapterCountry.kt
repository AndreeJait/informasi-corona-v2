package student.if419014.informasicorona.adapter

import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import student.if419014.informasicorona.DetailActivity
import student.if419014.informasicorona.R
import student.if419014.informasicorona.model.response.GetCountriesAll
import java.text.DecimalFormat

class AdapterCountry(
    val context: Context,
    val countries: List<GetCountriesAll>
) : RecyclerView.Adapter<AdapterCountry.Holder>() {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int, country:GetCountriesAll){
            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("country", country)
                itemView.context.startActivity(intent)
            }
            val decimalFormat = DecimalFormat("#,###")
            val imgFlag = itemView.findViewById<ImageView>(R.id.imgFlag)
            Picasso.with(itemView.context.applicationContext).load(country.countryInfo?.flag)
                .into(imgFlag)
            itemView.findViewById<TextView>(R.id.tvCountryName).text = country.country

            itemView.findViewById<TextView>(R.id.txtcases).text =
                "%s\n%s".format(itemView.resources.getString(R.string.cases),decimalFormat.format(country.cases))
            itemView.findViewById<TextView>(R.id.txttodaycases).text =
                "%s\n%s".format(itemView.resources.getString(R.string.today_cases),decimalFormat.format(country.todayCases))

            itemView.findViewById<TextView>(R.id.txtdeath).text =
                "%s\n%s".format(itemView.resources.getString(R.string.death),decimalFormat.format(country.deaths))
            itemView.findViewById<TextView>(R.id.txttodaydeath).text =
                "%s\n%s".format(itemView.resources.getString(R.string.today_death),decimalFormat.format(country.todayCases))

            itemView.findViewById<TextView>(R.id.txtrecovered).text =
                "%s\n%s".format(itemView.resources.getString(R.string.recovered),decimalFormat.format(country.recovered))
            itemView.findViewById<TextView>(R.id.txttodayrecovered).text =
                "%s\n%s".format(itemView.resources.getString(R.string.today_recovered),decimalFormat.format(country.todayRecovered))
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_countries, parent, false)
        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(position, countries[position])

    override fun getItemCount(): Int = countries.size
}