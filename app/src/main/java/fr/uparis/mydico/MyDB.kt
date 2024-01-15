package fr.uparis.mydico

import android.content.Context
import androidx.room.*

@Database(entities = [Dictionary::class, Word::class], version = 6)
abstract class MyDB : RoomDatabase() {
    abstract fun myDao(): MyDao

    companion object {

        @Volatile
        private var instance: MyDB? = null

        fun getDatabase(context: Context): MyDB {
            if(instance != null){
                return instance!!
            }
            val db = Room.databaseBuilder(
                context.applicationContext,
                MyDB::class.java, "dico"
            )
                .fallbackToDestructiveMigration()
                .build()
            instance = db
            return instance!!
        }
    }
}