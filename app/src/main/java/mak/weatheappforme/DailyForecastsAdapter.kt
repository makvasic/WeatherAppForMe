package mak.weatheappforme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_daily_forecast.view.*
import kotlinx.android.synthetic.main.item_hourly_forecast.view.temperatureTextView

class DailyForecastsAdapter :
    ListAdapter<DailyForecastModel, DailyForecastsAdapter.DailyForecastModelHolder>(object :
        DiffUtil.ItemCallback<DailyForecastModel>() {
        override fun areItemsTheSame(oldItem: DailyForecastModel, newItem: DailyForecastModel): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: DailyForecastModel, newItem: DailyForecastModel): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastModelHolder {
        return DailyForecastModelHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_daily_forecast, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DailyForecastModelHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DailyForecastModelHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dailyForecastModel: DailyForecastModel) = with(itemView) {
            temperatureTextView.text = dailyForecastModel.temperature
            dayIconImageView.setImageResource(dailyForecastModel.dayIcon)
            nightIconImageView.setImageResource(dailyForecastModel.nightIcon)
            dateTextView.text = dailyForecastModel.date
        }
    }
}