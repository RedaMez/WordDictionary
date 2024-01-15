package fr.uparis.mydico

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

class AddDictViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = (application as DictionaryApplication).database.myDao()

    companion object {
        const val TAG = "Add"
    }

    fun loadAll() = dao.loadDictionary()

    fun insertDict(name: String, src: String, dst: String){
        Thread {
            val l = dao.insertDict(Dictionary(name, src, dst))
            Log.d(TAG, "l : $l")
        }.start()
    }


}
