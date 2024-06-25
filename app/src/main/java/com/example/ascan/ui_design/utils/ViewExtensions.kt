package com.example.ascan.ui_design.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Calendar.toDisplay():String
{
//    func return a string
//    it will format to a readable date
    val dateFormat= SimpleDateFormat("dd-mm-yyyy hh:mm:a", Locale.US)
    return dateFormat.format(this.time)
}