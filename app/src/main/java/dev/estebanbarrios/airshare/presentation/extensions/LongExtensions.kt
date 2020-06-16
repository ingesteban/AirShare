package dev.estebanbarrios.airshare.presentation.extensions

import java.util.*
import java.util.concurrent.TimeUnit

fun Long.millisecondToDate(): Date {
    return Date(this)
}

fun Long.millisecondToDateReset(): Date {
    val date = Date(this)
    val calendar = GregorianCalendar()
    calendar.time = date
    calendar[Calendar.HOUR_OF_DAY] = 23
    calendar[Calendar.MINUTE] = 59
    calendar[Calendar.SECOND] = 59
    calendar[Calendar.MILLISECOND] = 999

    return calendar.time
}

fun Long.toMins(): String {
    return String.format(
        "%02d min, %02d sec",
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))
    )
}

fun Long.toMinsSecs(): String {
    return String.format(
        "%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))
    )
}

fun Long.toMB(): String {
    return (this / (1024 * 1024)).toString() + " mb"
}