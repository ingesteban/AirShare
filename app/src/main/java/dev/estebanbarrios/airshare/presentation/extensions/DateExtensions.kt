package dev.estebanbarrios.airshare.presentation.extensions

import android.annotation.SuppressLint
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Convert a Date to String with format: MMMM d, yyyy
 * i.e. January 3, 2020
 */
@SuppressLint("SimpleDateFormat")
fun Date.toLongDate(): String {
    try {
        return SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).format(this)
    } catch (ex: ParseException) {
        Log.e("ParseException", ex.localizedMessage)
    } catch (ex: Exception) {
        Log.e("Exception", ex.localizedMessage)
    }

    return ""
}

fun Date.toGroupDate(): String {
    try {
        return SimpleDateFormat("MMddYY", Locale.ENGLISH).format(this)
    } catch (ex: ParseException) {
        Log.e("ParseException", ex.localizedMessage)
    } catch (ex: Exception) {
        Log.e("Exception", ex.localizedMessage)
    }

    return ""
}
