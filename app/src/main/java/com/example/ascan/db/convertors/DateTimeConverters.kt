package com.example.ascan.db.convertors

import androidx.room.TypeConverter
import java.util.Calendar

class DateTimeConverters {
    @TypeConverter
    fun toCalender(I:Long):Calendar?{
        val calender=Calendar.getInstance()
        calender.timeInMillis=I
        return calender
    }
    @TypeConverter
    fun fromCalender(calendar: Calendar?):Long?{
        return calendar?.time?.time

    }
}