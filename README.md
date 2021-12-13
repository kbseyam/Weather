# Weather
#### Video Demo: https://www.youtube.com/watch?v=urMOBd3AAIg
#### Description:

used in project:

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
    import android.content.Context
    import android.database.sqlite.SQLiteDatabase
    import android.database.sqlite.SQLiteOpenHelper
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import androidx.core.content.ContextCompat.startActivity
    import androidx.recyclerview.widget.RecyclerView
    import android.database.Cursor
    import android.os.Bundle
    import android.util.Log
    import android.view.View
    import android.view.ViewManager
    import com.google.android.material.dialog.MaterialAlertDialogBuilder
    import com.google.android.material.textfield.TextInputLayout

MainActivity.kt is the main file that fetches and shows weather data inside of it:

    getWeather function fetches data from API
    loadIcon function set icon into imageView with Glide

    private fun loadIcon(icUrl: String) {
        Glide.with(this).load(icUrl)
            .into(binding.imgIc)



CitiesActivity.kt is the file that shows the city selection menu:

    launchAddDialog function launch dialog for add new city into database
    addNewCity function add new city into database
    getData function get data from data base and return list

    private fun getData(): MutableList<City> {
        val cursor: Cursor = fetch()
        val list: MutableList<City> = mutableListOf()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            list.add(City(cursor.getInt(0), cursor.getString(1), cursor.getString(2)))
            cursor.moveToNext()
        }

        return list
    }

DbHelper He creates the database:

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE ${Db.TABLE_NAME} (" +
                "${Db.ID} INTEGER NOT NULL," +
                "${Db.CITY} TEXT NOT NULL," +
                "${Db.COUNTRY} TEXT," +
                "PRIMARY KEY(${Db.ID} AUTOINCREMENT));"
        db?.execSQL(sql)
    }

CitiesAdaptor.kt is the file that provides the database and puts it in the list:

    deleteCity function delete city from database
    private fun deleteCity(id: Int) {
        CitesActivity.helper.writableDatabase.execSQL("DELETE FROM ${Db.TABLE_NAME} WHERE ${Db.ID}='${id}'")
    }


![This is an image](https://drive.google.com/file/d/11Yl-KdOhn0dGPVAXuKhiuw5IpHMm9k41/view?usp=sharing)



This program works on Android version 5 and above

My name is Khaled Bassam Seyam

from Palestine,

I live in Saudi Arabia, Riyadh

This program was developed by Khaled Bassam Seyam

on 12/13/2021

Github username: kbseyam

Gmail: kbseyam@gmail.com

Outlook: kbseyam@outlook.com

