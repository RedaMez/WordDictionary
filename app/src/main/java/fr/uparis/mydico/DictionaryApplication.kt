package fr.uparis.mydico

import android.app.Application

class DictionaryApplication : Application() {

    val database by lazy {
        MyDB.getDatabase(this)
    }

}