package fr.uparis.mydico

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Word(
    @PrimaryKey val mot:String,
    val language_src:String,
    val language_dest:String,
    val http_link:String
)
@Entity
data class Dictionary(
    @PrimaryKey val name:String,
    val language_src:String,
    val language_dest:String,

    )
