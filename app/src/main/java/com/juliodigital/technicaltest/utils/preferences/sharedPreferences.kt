package com.juliodigital.technicaltest.utils.preferences

import android.content.Context
import androidx.core.content.edit

fun Context.setPermissionRequested(value: Boolean) {
    val prefs = getSharedPreferences("permissions_prefs", Context.MODE_PRIVATE)
    prefs.edit{ putBoolean("permission_requested", value) }
}

fun Context.wasPermissionRequested(): Boolean {
    val prefs = getSharedPreferences("permissions_prefs", Context.MODE_PRIVATE)
    return prefs.getBoolean("permission_requested", false)
}
