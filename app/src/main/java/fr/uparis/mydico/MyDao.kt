package fr.uparis.mydico

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertMots(vararg mots: Word): List<Long>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertDict(vararg dict: Dictionary): List<Long>

    @Query("SELECT * FROM Dictionary")
    fun loadDict(): LiveData<List<Dictionary>>

    @Query("SELECT * FROM Word")
    fun loadWord(): LiveData<List<Word>>


    @Query( "DELETE FROM Word WHERE mot = :mot")
    fun deleteWord(mot : String) : Int



    @Query("SELECT * FROM Dictionary")
    fun loadDictionary(): LiveData<List<Dictionary>>

    @Query("SELECT * FROM Dictionary WHERE name like :nom || '%' ")
    fun loadPartialNameDictionary(nom: String): LiveData<List<Dictionary>>
    /*
        @Query("SELECT * FROM Word")
        fun loadWord(): LiveData<List<Word>>
    */
    @Query( "DELETE FROM Dictionary WHERE name = :nom")
    fun delete(nom : String) : Int


}