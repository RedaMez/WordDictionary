package fr.uparis.mydico

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import java.util.*


open class WordsService : ViewStoreOwnerService() {

    private val CHANNEL_ID = "channel"
    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    private val pendingFlag = PendingIntent.FLAG_IMMUTABLE

    private var nb_mots = 0
    private var freq = 0
    private var minute = 0
    private var hour = 0
    private var day = 0
    private var month = 0
    private var year = 0

    var cpt=0

    private lateinit var sharedPref : SharedPreferences
    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)

        TODO("Return the communication channel to the service.")
    }

    @SuppressLint("ServiceCast", "UnspecifiedImmutableFlag")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val dao = (application as DictionaryApplication).database.myDao()
        val allWord: LiveData<List<Word>> = dao.loadWord()
        super.onStartCommand(intent, flags, startId)
        intent!!.extras
        sharedPref = this.getSharedPreferences("preference", Context.MODE_PRIVATE)
        nb_mots = sharedPref.getInt("nb_mots", 0)
        freq = sharedPref.getInt("freq", 0)
        cpt = nb_mots
        minute = sharedPref.getInt("minute", 0)
        hour = sharedPref.getInt("hour", 0)
        day = sharedPref.getInt("day", 0)
        month = sharedPref.getInt("month", 0)
        year = sharedPref.getInt("year", 0)

        val intentNotif = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 1, intentNotif,
            pendingFlag
        )

        if (freq != -1) {
            allWord.observe(this) {
                val action = intent.action
                if (action == "swipe") {
                    val idd = intent.extras?.getInt("id")
                    val url = "google"
                    val webPage: Uri = Uri.parse("$url")
                    val browserIntent = Intent(Intent.ACTION_VIEW, webPage)
                    val phrase = allWord.value?.get(cpt)?.mot.toString()

                    val pending_browser = PendingIntent.getActivity(
                        this, 0, browserIntent,
                        pendingFlag
                    )
                    val notif = create_notif(phrase, cpt, pending_browser, pendingIntent)
                    notificationManager.notify(cpt, notif)
                    createNotificationChannel()
                    cpt++
                    nb_mots--

                } else {
                    for (i in 0 until nb_mots) {
                        val phrase = allWord.value?.get(i)?.mot.toString()
                        val url =
                            allWord.value?.get(i)?.http_link?.replace(" ", "_")?.lowercase()
                        val webPage: Uri = Uri.parse("$url")
                        val browserIntent = Intent(Intent.ACTION_VIEW, webPage)

                        val pending_browser = PendingIntent.getActivity(
                            this, 0, browserIntent,
                            pendingFlag
                        )

                        val notification =
                            create_notif(phrase, i, pending_browser, pendingIntent)
                        notificationManager.notify(i, notification)


                        with(sharedPref.edit()) {
                            putInt("freq", freq)
                            apply()
                        }
                        createNotificationChannel()

                    }
                }
            }

            val time = getTime()
            //Log.d("timee","$time")

            val intentAlarm = Intent(this, WordsService::class.java)
            val pendingIntent2 =
                PendingIntent.getService(this, 1, intentAlarm, PendingIntent.FLAG_IMMUTABLE)
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                time,
                //SystemClock.elapsedRealtime() + freq * 1000,
                pendingIntent2
            )
            return START_NOT_STICKY
        }
        stopSelf()

        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, "channel_name",
            NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "channel_description"
        notificationManager.createNotificationChannel(channel)
    }


    private fun getTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    @SuppressLint("UnspecifiedImmutableFlag")
   fun create_notif(word:String, id: Int, pending_browser :PendingIntent, pendingIntent:PendingIntent ) : Notification {

        val deleteIntent = Intent(this,NotificationDismissedReceiver::class.java)
        deleteIntent.putExtra("id_swiped",id)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Words$id")
            .setContentText(word)
            .setContentIntent(pendingIntent)
            .addAction(
                R.drawable.small,
                "link",
                pending_browser
            )
            .setAutoCancel(true)
           .setDeleteIntent(PendingIntent.getBroadcast(this, id, deleteIntent, PendingIntent.FLAG_CANCEL_CURRENT))
            .setSmallIcon(R.drawable.small)
            .build()
        return notification
    }
}