package com.core.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.formatDate(): String {

    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(formatter.parse(this))
}
