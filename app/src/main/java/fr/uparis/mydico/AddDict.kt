package fr.uparis.mydico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import fr.uparis.mydico.databinding.ActivityAddDictBinding

class AddDict : AppCompatActivity() {
    lateinit var binding: ActivityAddDictBinding
    private val model by lazy {
        ViewModelProvider(this)[AddDictViewModel::class.java].apply{}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDictBinding.inflate(layoutInflater)
        setContentView( binding.root)


        val src = intent.getStringExtra("src").toString()
        if(src =="null"){ binding.edSource.setText("")}
        else{binding.edSource.setText(src)}

        val dest = intent.getStringExtra("dest").toString()
        if(src =="null") {binding.edDestination.setText("") }
        else{binding.edDestination.setText(dest)}


    }

    fun ajouter(view: View) {
        val name = binding.edName.text.toString().trim()
        val src = binding.edSource.text.toString().trim()
        val dst = binding.edDestination.text.toString().trim()

        if(name == ""){
            afficherDialog("Name is empty")
            binding.edName.requestFocus()
            return
        }else if(src == ""){
            afficherDialog("Source language is empty")
            binding.edSource.requestFocus()
            return
        } else if (dst == ""){
            afficherDialog("Destination language is empty")
            binding.edDestination.requestFocus()
            return
        }

        model.insertDict(name, src, dst)
        binding.edName.text.clear()
        binding.edSource.text.clear()
        binding.edDestination.text.clear()
        Toast.makeText(
            this,
            "Dictionary added",
            Toast.LENGTH_SHORT
        ).show()

        model.loadAll().observe(this) { binding.edName }
    }

    private fun afficherDialog(s: String) {
        AlertDialog.Builder(this)
            .setMessage(s)
            .setPositiveButton("OK") { d, _ -> d.dismiss() }
            .setCancelable(false)
            .show()
        return
    }
}