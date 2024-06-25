package com.example.ascan.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ascan.db.dao.ScanResultDAO
import com.example.ascan.db.entities.ScanResult

@Database(entities = [ScanResult::class], version = 1, exportSchema = false)
abstract class ScanResultDataBase:RoomDatabase() {
    abstract fun getScanDAO():ScanResultDAO
    companion object{
//        we have to create the static function to create database objects
        private const val DATABASE_NAME="ScanResultDataBase"
        private var scanResultDatabase: ScanResultDataBase? = null
        fun getAppDatabase(context:Context): ScanResultDataBase?{
            if(scanResultDatabase==null){
                scanResultDatabase= Room.databaseBuilder(
                    context.applicationContext,
                    ScanResultDataBase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries().build()
            }
            return scanResultDatabase
        }

    }

}