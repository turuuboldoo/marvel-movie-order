package mn.turbo.marvel.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AppPreference(
    context: Context
) {

    companion object {
        private const val TAG = "AppPreference"
        private const val WIDTH = "width"
        private const val HEIGHT = "height"
    }

    private var preference: SharedPreferences =
        context.getSharedPreferences(TAG, Context.MODE_PRIVATE)

    fun putDeviceWidth(width: Int) = preference.edit(true) {
        putInt(WIDTH, width)
    }

    fun putDeviceHeight(height: Int) = preference.edit(true) {
        putInt(HEIGHT, height)
    }

    val deviceWidth = preference.getInt(WIDTH, 0)
    val deviceHeight = preference.getInt(HEIGHT, 0)
}
