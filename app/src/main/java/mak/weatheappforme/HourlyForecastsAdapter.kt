package mak.weatheappforme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_hourly_forecast.view.*

class HourlyForecastsAdapter :
    ListAdapter<HourlyForecastModel, HourlyForecastsAdapter.HourlyForecastModelHolder>(object :
        DiffUtil.ItemCallback<HourlyForecastModel>() {
        override fun areItemsTheSame(oldItem: HourlyForecastModel, newItem: HourlyForecastModel): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: HourlyForecastModel, newItem: HourlyForecastModel): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastModelHolder {
        return HourlyForecastModelHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_hourly_forecast, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HourlyForecastModelHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HourlyForecastModelHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(hourlyForecastModel: HourlyForecastModel) = with(itemView) {
            temperatureTextView.text = hourlyForecastModel.temperature
            iconImageView.setImageResource(hourlyForecastModel.weatherIcon)
            precipitationTextView.text = hourlyForecastModel.precipitationProbability
            timeTextView.text = hourlyForecastModel.time
        }
    }
}