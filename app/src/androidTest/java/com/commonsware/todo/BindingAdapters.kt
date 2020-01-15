package com.commonsware.todo

import android.text.format.DateUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*

@BindingAdapter("formattedDate")
fun TextView.formattedDate(date: Calendar?) {
    date?.let {
        text = DateUtils.getRelativeDateTimeString(
            context,
            date.timeInMillis,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.WEEK_IN_MILLIS,
            0
        )
    }
}
