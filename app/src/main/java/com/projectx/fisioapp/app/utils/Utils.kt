package com.projectx.fisioapp.app.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


fun toastIt(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    Log.d("App", msg) // Debug
}

enum class CatalogType {
    SERVICE,
    PRODUCT
}

fun formatDate(date: Date) : String {

    val format = SimpleDateFormat("EEEE, dd/MM/yyy - HH:mm")
    return format.format(date)
}

fun formatValue(value: Int): String {
    var valueFormatted = "$value"
    when(value){
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ->   valueFormatted = "0"+valueFormatted
    }
    return valueFormatted
}

