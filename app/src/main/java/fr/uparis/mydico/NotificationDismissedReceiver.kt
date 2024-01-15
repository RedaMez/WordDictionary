package fr.uparis.mydico

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log



class NotificationDismissedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val id = intent.extras!!.getInt("id_swiped")
        Log.d("testswiped","$id")

        val serviceIntent = Intent(context, WordsService::class.java)
        serviceIntent.action = "swipe";
        serviceIntent.putExtra("id", id)
        context?.startService(serviceIntent)


    }


}