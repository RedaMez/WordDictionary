package fr.uparis.mydico


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class AddWordView(application: Application) : AndroidViewModel(application) {

    private val dao = (application as DictionaryApplication).database.myDao()
    var checkedWords : Word? = null


    companion object {
        const val TAG = "Add"
    }
    val allWord : LiveData<List<Word>> = dao.loadWord()




    fun deleteWord(){
        Thread {
            dao.deleteWord(checkedWords!!.mot)
        }.start()
    }



    fun loadAllWords() = dao.loadWord()

    fun insertWord(mot: String, lang_src: String, lang_dst: String,link:String){
        Thread {
            val l = dao.insertMots(Word (mot, lang_src, lang_dst,link))
        }.start()
    }


}