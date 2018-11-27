package pt.isel.pdm.jht.notifbat

import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.arch.lifecycle.MutableLiveData
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Build.VERSION_CODES.O
import android.support.v4.app.NotificationManagerCompat

class NotifApp : Application() {

    val batLevel = MutableLiveData<Int>()

    override fun onCreate() {
        super.onCreate()
        registerForBatteryChanges()
    }

    private fun registerForBatteryChanges() {

        val batteryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val level = getBatteryLevel(intent)
                batLevel.value = level

                val notification = notificationBuilder(context, "BatLevel")
                        .setSmallIcon(android.R.drawable.star_on)
                        .setContentTitle("Battery Level")
                        .setContentText("$level%")
                        .build()

                NotificationManagerCompat.from(context)
                        .notify(8, notification)
            }
        }

        val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        registerReceiver(batteryReceiver, batteryFilter)
    }

    private fun getBatteryLevel(intent: Intent) : Int {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        var result = -1
        if (level >= 0 && scale >= 1) {
            result = (level * 100 / scale.toDouble()).toInt()
        }
        return result
    }

    private fun notificationBuilder(context: Context, channelId: String) : Notification.Builder {
        if (SDK_INT >= O) {
            return Notification.Builder(context, channelId)
        } else {
            return Notification.Builder(context);
        }
    }
}
