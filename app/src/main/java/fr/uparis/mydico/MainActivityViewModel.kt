package fr.uparis.mydico

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = (application as DictionaryApplication).database.myDao()

    var checkedDictionary : Dictionary? = null

    val allDictionary : LiveData<List<Dictionary>> = dao.loadDictionary()

    var partialDictionary : LiveData<List<Dictionary>>? = null

    fun loadPartialNameDictionary(prefix : String){
        partialDictionary = dao.loadPartialNameDictionary(prefix)
    }

    fun deleteDict(){
        Thread {
            dao.delete(checkedDictionary!!.name)
        }.start()
    }



}
