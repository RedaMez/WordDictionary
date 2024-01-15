package fr.uparis.mydico

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import fr.uparis.mydico.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val model by lazy { ViewModelProvider(this)[MainActivityViewModel::class.java].apply{} }
    private val adapter by lazy{ MainActivityAdapter(model) }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        setSupportActionBar( binding.myToolbar )
        model.allDictionary.observe(this){
            adapter.listDictionary = it
            adapter.notifyDataSetChanged()
        }

        // getSupportActionBar().setIcon(getDrawable(R.drawable.))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_layout, menu)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem) : Boolean = when
                                                                           ( item.itemId ) {
        R.id.add_word -> {
            val intent = Intent(this,AddWord::class.java)
            startActivity(intent)
            true
        }
       R.id.add_dict ->{
            val intent = Intent(this,AddDict::class.java)
           startActivity(intent)
            true
        }
        R.id.quitter ->{
            finish()
            true
        }
        R.id.Tools ->{
            val intent = Intent(this,Aprentissage::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun google_search(view: View) {
        val intentURL = Intent(android.content.Intent.ACTION_VIEW)
        val lien = "http://www.google.fr/search?q="
        if (model.checkedDictionary != null) {
            val src = model.checkedDictionary?.language_src.toString()
            val dest = model.checkedDictionary?.language_dest.toString()
            val mot = binding.mot.text.toString()
            val intent_url = Intent(this, AddWord::class.java).apply {
                this.putExtra("mot", mot);
            }
            intentURL.data = Uri.parse("$lien$mot+$src+$dest")
        } else {
            val mot = binding.mot.text.toString()
            val intent_url = Intent(this, AddWord::class.java).apply {
                this.putExtra("mot", mot);

            }
            intentURL.data = Uri.parse(lien + mot)
        }
        startActivity(intentURL)
    }

    }
