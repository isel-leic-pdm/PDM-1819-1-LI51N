package pt.isel.pdm.jht.placard

import android.app.Application
import android.content.SharedPreferences
import androidx.multidex.MultiDexApplication
import java.util.*

class PlacardApp  : MultiDexApplication() {

    val appPlacard by lazy { Placard(this, deviceId) }

    val deviceId by lazy { getDeviceId(getSharedPreferences("prefs", 0)) }

    private val DEV_ID = "DEV_ID"

    private fun getDeviceId(sharedPreferences: SharedPreferences) =
            sharedPreferences
                    .getString(DEV_ID, null) ?:
                    generateDeviceId(sharedPreferences)

    private fun generateDeviceId(sharedPreferences: SharedPreferences) : String {
        val devId = UUID.randomUUID().toString()
        sharedPreferences
                .edit()
                .putString(DEV_ID, devId)
                .apply()
        return devId
    }
}

val Application.placard : Placard
    get() = (this as PlacardApp).appPlacard
