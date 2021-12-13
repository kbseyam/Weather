/*
welcome
My name is Khaled Bassam Seyam from Palestine, I live in Saudi Arabia, Riyadh
This program was developed by Khaled Bassam Seyam on 12/13/2021
Github username: kbseyam
Gmail: kbseyam@gmail.com
Outlook: kbseyam@outlook.com
 */

package com.khalid.weather

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.khalid.weather.databinding.ActivityMainBinding
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val key = "6a8831c8f7be56b2a1dddd0480da372b"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val inCity = intent.getStringExtra("city")
        val inCountry = intent.getStringExtra("country")

        if (inCity.isNullOrEmpty()) {
            hideViews()
        } else {
            getWeather(inCity, inCountry)
        }

        binding.buttonCity.setOnClickListener {
            val bIntent = Intent(this, CitesActivity()::class.java)
            startActivity(bIntent)
            this.finish()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getWeather(mCity: String, mCountry: String?) {
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=$mCity,,$mCountry&units=metric&appid=$key"
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {
                val jsonArray = it.getJSONArray("weather")
                val jsonObjectWeather = jsonArray.getJSONObject(0)
                val description = jsonObjectWeather.getString("description")
                val icon = jsonObjectWeather.getString("icon")
                val jsonObjectMain = it.getJSONObject("main")
                val temp = jsonObjectMain.getInt("temp").toString() + "°C"
                val feelsLike = jsonObjectMain.getDouble("feels_like")
                val pressure = jsonObjectMain.getInt("pressure")
                val humidity = jsonObjectMain.getInt("humidity")
                val jsonObjectWind = it.getJSONObject("wind")
                val wind = jsonObjectWind.getDouble("speed")
                val jsonObjectSys = it.getJSONObject("sys")
                val country = jsonObjectSys.getString("country")
                val city = it.getString("name")

                val icUrl = "https://openweathermap.org/img/wn/${icon}@4x.png"
                loadIcon(icUrl)
                binding.tvTemp.text = temp
                binding.tvDescription.text = description
                binding.tvFeelsLike.text = round(feelsLike).toInt().toString() + "°C"
                binding.tvPressure.text = pressure.toString() + "hPa"
                binding.tvHumidity.text = "$humidity%"
                binding.tvWind.text = wind.toString() + "m/s"
                binding.tvCity.text = city
                binding.tvCountry.text = country
            }, {
                hideViews()
                binding.textHint.text = "No city found with this name"
                Log.e("GET_WEATHER", it.toString())
            })
        queue.add(jsonObjectRequest)
    }

    private fun loadIcon(icUrl: String) {
        Glide.with(this).load(icUrl)
            .into(binding.imgIc)
    }

    private fun hideViews() {
        binding.textDescription.visibility = View.INVISIBLE
        binding.textFeelsLike.visibility = View.INVISIBLE
        binding.textHumidity.visibility = View.INVISIBLE
        binding.textPressure.visibility = View.INVISIBLE
        binding.textWind.visibility = View.INVISIBLE
        binding.textHint.visibility = View.VISIBLE
    }
}