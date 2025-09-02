package br.com.fernandodev.hinovaoficinas.core.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun String.decodeBase64Bitmap(): Bitmap? = try {
    val bytes = Base64.decode(this, Base64.DEFAULT)
    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
} catch (_: Throwable) { null }
