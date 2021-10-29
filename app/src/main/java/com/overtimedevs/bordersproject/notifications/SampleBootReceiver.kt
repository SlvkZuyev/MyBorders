package com.overtimedevs.bordersproject.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class SampleBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            MyAlarmManager.setup(context)
        }
    }
}
