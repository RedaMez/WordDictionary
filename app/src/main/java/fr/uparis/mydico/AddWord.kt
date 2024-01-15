package fr.uparis.mydico

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import fr.uparis.mydico.databinding.ActivityAddWordBinding

class AddWord : AppCompatActivity() {
    private val binding by lazy{ ActivityAddWordBinding.inflate( layoutInflater )  }

    private val model by lazy { ViewModelProvider(this)[AddWordView::class.java].apply{} }
    private val adapter by lazy{ AddWordAdapter(model) }
    private lateinit var parent: Intent
     var string_words= arrayOf("a")



    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        model.allWord.observe(this){
            adapter.listWords = it
            adapter.notifyDataSetChanged()
        }
        if( intent.action.equals( "android.intent.action.SEND" ) ){
            val txt = intent.extras?.getString( "android.intent.extra.TEXT" )
            val link =binding.link
            link.setText(txt)

        }
        setContentView( binding.root)
        setSupportActionBar( binding.wordToolbar )

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.add_word_menu, menu)
        return true
    }

    fun ajouter(view: View) {
        val mot = binding.mot.text.toString().trim()
        val src = binding.src.text.toString().trim()
        val dest = binding.dest.text.toString().trim()
        val link= binding.link.text.toString().trim()

        if(mot == ""){
            afficherDialog("Word is empty")
            binding.mot.requestFocus()
            return
        }else if(src == ""){
            afficherDialog("Source language is empty")
            binding.src.requestFocus()
            return
        } else if (dest == "") {
            afficherDialog("Destination language is empty")
            binding.dest.requestFocus()
            return
        }else if (link=="") {
            afficherDialog("link is empty")
            binding.link.requestFocus()
            return
        }

        model.insertWord(mot, src, dest,link)
        val s = mot+src+dest+link
        string_words+=s
        Log.d("hh",string_words[0])



        binding.mot.text.clear()
        binding.src.text.clear()
        binding.dest.text.clear()
        binding.link.text.clear()
        Toast.makeText(
            this,
            "Word added",
            Toast.LENGTH_SHORT
        ).show()

        model.loadAllWords().observe(this) { binding.mot }

        val intent_dict= Intent(this, AddDict::class.java).apply {
            this.putExtra("src", src);
            Log.d("src", src)
            this.putExtra("dest", dest)

            }
        startActivity(intent_dict)




    }



    override fun onOptionsItemSelected(item: MenuItem) : Boolean = when ( item.itemId ) {
        R.id.retour-> {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
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

/*package com.geeksforgeeks.myfirstkotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // access the items of the list
        val languages = resources.getStringArray(R.array.Languages)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter

         spinner.onItemSelectedListener = object :
           AdapterView.OnItemSelectedListener {
             override fun onItemSelected(parent: AdapterView<*>,
                                         view: View, position: Int, id: Long) {
                 Toast.makeText(this@MainActivity,
                    getString(R.string.selected_item) + " " +
                         "" + languages[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }
}*/
