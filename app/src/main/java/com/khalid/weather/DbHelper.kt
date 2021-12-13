package com.khalid.weather

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE ${Db.TABLE_NAME} (" +
                "${Db.ID} INTEGER NOT NULL," +
                "${Db.CITY} TEXT NOT NULL," +
                "${Db.COUNTRY} TEXT," +
                "PRIMARY KEY(${Db.ID} AUTOINCREMENT));"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val DB_NAME = "CityDatabase"
        private const val DB_VERSION = 1
    }

}
