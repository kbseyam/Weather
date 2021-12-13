package com.khalid.weather

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.khalid.weather.databinding.ActivityCitesBinding

class CitesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCitesBinding
    private lateinit var adapter: CitiesAdapter
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var customAlertDialogView: View

    companion object {
        lateinit var helper: DbHelper

        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
    }

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCitesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helper = DbHelper(this)
        mContext = this
        customAlertDialogView = layoutInflater.inflate(R.layout.dialog_add, null, false)
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
        adapter = CitiesAdapter(getData())
        binding.recyclerCity.adapter = adapter

        binding.fabAdd.setOnClickListener {
            launchAddDialog()
        }
    }

    private fun launchAddDialog() {
        val etCity = customAlertDialogView.findViewById<TextInputLayout>(R.id.et_city)
        val etCountry = customAlertDialogView.findViewById<TextInputLayout>(R.id.et_country)

        if (customAlertDialogView.parent != null) {
            (customAlertDialogView.parent as ViewManager).removeView(customAlertDialogView)
        }

        materialAlertDialogBuilder.setView(customAlertDialogView).setTitle("Add city")
            .setPositiveButton("Add") { _, _ ->
                val city = etCity.editText?.text.toString().trim()
                val country = etCountry.editText?.text.toString().trim()

                try {
                    addNewCity(city, country)
                } catch (ex: Exception) {
                    Log.e("ADD_CITY", "Error")
                }

                adapter = CitiesAdapter(getData())
                binding.recyclerCity.adapter = adapter

            }
            .setNegativeButton("Cancel") { dialog, _ ->

                dialog.dismiss()
            }
            .show()
    }

    private fun addNewCity(city: String, country: String) {
        val newEntry = ContentValues().apply {
            put(Db.CITY, city)
            put(Db.COUNTRY, country)
        }
        DbHelper(this).writableDatabase.insert(Db.TABLE_NAME, null, newEntry)
    }

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

    private fun fetch(): Cursor {
        val projection = arrayOf(Db.ID, Db.CITY, Db.COUNTRY)
        val cursor = DbHelper(this).readableDatabase.query(
            Db.TABLE_NAME, projection,
            null, null, null, null, null, null
        )

        cursor.moveToFirst()

        return cursor
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity()::class.java)
        startActivity(intent)
    }
}