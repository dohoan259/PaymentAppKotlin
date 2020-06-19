package me.hp.paymentapp.common

import android.content.Context
import android.graphics.Color
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.os.Build
import android.app.Activity
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY
import java.text.SimpleDateFormat
import java.util.*


fun changeStatusBarIconColor(activity: Activity, isLightStatusBar: Boolean = true) {
    val decor = activity.window.decorView
    var flag = activity.window.decorView.systemUiVisibility
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (isLightStatusBar) {

            flag = flag or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            decor.systemUiVisibility = flag
        } else {
            flag = flag and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            //                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            decor.systemUiVisibility = flag
        }
    }
}

fun changeStatusBarColor(activity: Activity, color:Int = Color.TRANSPARENT) {
    var flags = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    flags = flags or SYSTEM_UI_FLAG_LAYOUT_STABLE
    activity.window.decorView.systemUiVisibility = flags

    activity.window.statusBarColor = color
}

fun showSoftKeyboard(context: Context, view: View) {
    if (view.requestFocus()) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, HIDE_IMPLICIT_ONLY)
    }
}

fun hideSoftKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun toDate(timestamp: Long, pattern: String): String {
    val c = Calendar.getInstance()
    c.timeInMillis = timestamp
    val d = c.time
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(d)
}