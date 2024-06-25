package com.example.ascan.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.ascan.db.convertors.DateTimeConverters
import java.util.Calendar
@Entity
@TypeConverters(DateTimeConverters::class)
data class ScanResult (
//   Scan Results  is a table which holds 4 columns which are result,result_type,favourite,time
//    we also need a id as primary key for all the results because we have to identify between them
    @PrimaryKey(autoGenerate = true)
    val id:Int ? =null,
    @ColumnInfo(name = "result")
    val result : String?,

    @ColumnInfo(name="result_type")
    val resultType: String?,

    @ColumnInfo(name="favourite")
    val favourite: Boolean = false,

    @ColumnInfo(name = "time")
    val calendar: Calendar
//    calender is not a type so we have to use type convertor

//    for accessing these database google provided us an interface which is "DAO"--->DATABASE ACCESS OBJECTS

)

//     we are having four things to store in our database
//     result
//     result type
//     favourite
//     time
