package com.khalid.weather

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.khalid.weather.databinding.ItemCityBinding

class CitiesAdapter(private val list: MutableList<City>) :
    RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CitiesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val currentCity = list[position]

        holder.binding.apply {
            tvCity.text = currentCity.city
            tvCountry.text = currentCity.country
            deleteImg.setOnClickListener {
                if (list.size > 1) {
                    deleteCity(currentCity.id)
                    list.removeAt(position)
                    notifyItemRemoved(position)
                } else {
                    Toast.makeText(
                        CitesActivity.mContext,
                        "At least one city must remain",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            cardItemCity.setOnClickListener {
                val intent = Intent(CitesActivity.mContext, MainActivity::class.java)
                intent.putExtra("city", currentCity.city)
                intent.putExtra("country", currentCity.country)
                startActivity(CitesActivity.mContext, intent, null)
            }
        }
    }

    private fun deleteCity(id: Int) {
        CitesActivity.helper.writableDatabase.execSQL("DELETE FROM ${Db.TABLE_NAME} WHERE ${Db.ID}='${id}'")
    }

    override fun getItemCount() = list.size

    class CitiesViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val binding = ItemCityBinding.bind(viewItem)
    }

}