package fr.uparis.mydico

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import fr.uparis.mydico.databinding.ActivityAddWordBinding
import fr.uparis.mydico.databinding.ActivityAprentissageBinding
import java.util.*

class Aprentissage : AppCompatActivity() {
    private lateinit var sharedPref : SharedPreferences
    private val binding by lazy{ ActivityAprentissageBinding.inflate( layoutInflater )  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root)
        sharedPref = this.getSharedPreferences("preference", Context.MODE_PRIVATE)


    }
    fun startService(view: View) {
        val freq = findViewById<EditText>(R.id.freq).text.toString().toInt()
        val nb_mots = findViewById<EditText>(R.id.nb_mots).text.toString().toInt()
        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year
        with (sharedPref.edit()) {
            putInt( "freq",freq )
            putInt("nb_mots",nb_mots)
            putInt("minute",minute)
            putInt("hour",hour)
            putInt("day",day)
            putInt("month",month)
            putInt("year",year)

            apply()
        }

        val intent = Intent(this, WordsService::class.java)
        startService(intent)
    }

    fun stopService(view: View) {
        with (sharedPref.edit()) {
            putInt("nb_mots", -1)
            putInt("freq",-1)
            commit()
        }
    }
}