package com.example.ascan.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ascan.db.entities.ScanResult

@Dao
interface ScanResultDAO {
//    here we write all the queries and stuffs here
//    we write all the equations which are implement in our projects

    @Query("SELECT * FROM ScanResult ORDER BY time DESC")
    fun getTotalScanResults():List<ScanResult>
//        it have return all the results of the Scan results
        @Query("SELECT * FROM ScanResult WHERE favourite=1")
        fun getTotalFavouriteScanResults():List<ScanResult>

        @Query("DELETE FROM ScanResult")
        fun DeleteTotalScanResults()

        @Query("DELETE FROM ScanResult WHERE favourite")
        fun DeleteTotalFavouriteScanResults()

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertScanResult(scanResult: ScanResult):Long

        @Query("SELECT * FROM ScanResult WHERE id = :id ")
        fun getScanResult(id:Int):ScanResult
//        here we send the the id and get the ScanResults
        @Query("UPDATE ScanResult SET favourite=1 WHERE id= :id")
        fun addToFavourite (id: Int):Int

        @Query("UPDATE ScanResult SET favourite=0 WHERE id= :id")
        fun removeFromFavourite (id: Int):Int

        @Query("DELETE FROM ScanResult WHERE id=:id")
        fun deleteScanResult(id: Int):Int













}