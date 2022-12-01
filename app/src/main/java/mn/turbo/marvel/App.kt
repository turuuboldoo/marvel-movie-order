package mn.turbo.marvel

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import mn.turbo.marvel.data.local.preference.AppPreference

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreference.getInstance(applicationContext)
    }
}
