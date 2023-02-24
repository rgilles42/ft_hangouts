package fortytwo.rgilles.ft_hangouts.common.presentation.util

import java.text.SimpleDateFormat
import java.util.*

fun getFormattedDate(timestamp : Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    val now = Calendar.getInstance()
    val dateFormat = if (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
        if (calendar.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
            SimpleDateFormat("HH:mm", Locale.getDefault())
        } else {
            SimpleDateFormat("MMM d, HH:mm", Locale.getDefault())
        }
    } else {
        SimpleDateFormat("MMM d yyyy, HH:mm", Locale.getDefault())
    }
    return dateFormat.format(calendar.time)
}