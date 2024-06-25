package com.example.ascan.db

import com.example.ascan.db.database.ScanResultDataBase
import com.example.ascan.db.entities.ScanResult
import java.util.Calendar

class DatabaseHelper( var scanResultDataBase: ScanResultDataBase) :DBHelper{
    override fun insertScanResult(result: String): Int {
        val time=Calendar.getInstance()
        val resultType="TEXT"
        val scanResult=ScanResult(result= result, resultType = resultType, calendar = time , favourite = false)
        return scanResultDataBase.getScanDAO().insertScanResult(scanResult).toInt()
    }

    override fun getScanResult(id: Int): ScanResult {
        return scanResultDataBase.getScanDAO().getScanResult(id)
    }

    override fun addToFavourite(id: Int): Int {
        return scanResultDataBase.getScanDAO().addToFavourite(id)
    }

    override fun removeFromFavourite(id: Int): Int {
        return scanResultDataBase.getScanDAO().removeFromFavourite(id)
    }

    override fun getTotalScanResults(): List<ScanResult> {
        return scanResultDataBase.getScanDAO().getTotalScanResults()
    }

    override fun getTotalFavouriteScanResults(): List<ScanResult> {
        return scanResultDataBase.getScanDAO().getTotalFavouriteScanResults()
    }

    override fun deleteScanResult(id: Int): Int {
        return scanResultDataBase.getScanDAO().deleteScanResult(id)
    }

    override fun removeAllScanResultsRecords() {
         scanResultDataBase.getScanDAO().DeleteTotalScanResults()
    }

    override fun removeAllFavouriteScanResultsRecords() {
        scanResultDataBase.getScanDAO().DeleteTotalFavouriteScanResults()
    }
}