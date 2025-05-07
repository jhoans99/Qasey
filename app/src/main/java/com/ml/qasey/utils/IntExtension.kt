package com.ml.qasey.utils

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun Int.convertToFormatTime(): String {
    val minutes = this / 60
    val remainingSeconds = this % 60
    return String.format("%02d:%02d",minutes,remainingSeconds)
}