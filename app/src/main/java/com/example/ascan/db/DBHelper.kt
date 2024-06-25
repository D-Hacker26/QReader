package com.example.ascan.db

import com.example.ascan.db.entities.ScanResult

interface DBHelper {
    fun insertScanResult(result: String):Int
    fun getScanResult(id:Int):ScanResult
    fun addToFavourite(id: Int):Int
    fun removeFromFavourite(id:Int):Int
    fun getTotalScanResults():List<ScanResult>
    fun getTotalFavouriteScanResults():List<ScanResult>
    fun deleteScanResult(id: Int):Int
    fun removeAllScanResultsRecords()
    fun removeAllFavouriteScanResultsRecords()

}