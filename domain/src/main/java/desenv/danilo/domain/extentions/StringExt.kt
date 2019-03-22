package desenv.danilo.domain.extentions

import java.text.SimpleDateFormat
import java.util.*


fun String.toFormaStringDate(pattern: String = "yyyy-MM-dd'T'HH:mm:ss"): String {
    val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val inputFormat = SimpleDateFormat(pattern, Locale.getDefault())

    val date = inputFormat.parse(this)
    return outputFormat.format(date)
}



