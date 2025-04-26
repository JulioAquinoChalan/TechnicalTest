package com.juliodigital.technicaltest.utils.extensions

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

fun File.saveBitmapToFile(bitmap: Bitmap) {
    FileOutputStream(this).use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
    }
}