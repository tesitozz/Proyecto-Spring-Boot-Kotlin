package com.proyecto.proyectoesfrt.data

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.proyecto.proyectoesfrt.utils.appConfig

class InitBD:SQLiteOpenHelper(appConfig.CONTEXTO,
                             appConfig.nombreBD,null,
                             appConfig.VERSION) {
    override fun onCreate(db: SQLiteDatabase) {}

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


}