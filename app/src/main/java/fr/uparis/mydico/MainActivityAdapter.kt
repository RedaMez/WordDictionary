package fr.uparis.mydico

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import fr.uparis.mydico.databinding.ItemLayoutBinding

class MainActivityAdapter(private val model: MainActivityViewModel) : RecyclerView.Adapter<MainActivityAdapter.VH>() {
    var listDictionary : List<Dictionary> = listOf()

    private var pref: String = ""

    class VH(var binding:ItemLayoutBinding) : RecyclerView.ViewHolder( binding.root ){
        lateinit var dictionary : Dictionary
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = VH(binding)
        holder.binding.root.setOnClickListener{
            val pays = holder.dictionary
            if (pays != model.checkedDictionary) {
                model.checkedDictionary = holder.dictionary
            } else {
                model.checkedDictionary = null
            }
            notifyDataSetChanged()
        }

        return holder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.dictionary = listDictionary[position]
        with(holder.binding){
            name.text = holder.dictionary.name
            src.text = holder.dictionary.language_src
            dest.text = holder.dictionary.language_dest
            delete.setOnClickListener{
                model.deleteDict();
            }
            changeColor(holder, position)
        }

    }





    private fun changeColor(holder: VH, position: Int) {
        holder.binding.root.setBackgroundColor(
            if(holder.dictionary == model.checkedDictionary){
                Color.argb(0.5f, 0.2f, 0.2f, 0.2f)
            }else if(position % 2 == 0) {
                Color.argb(0.1f, 0.3f, 0.3f, 0.0f)
            }else {
                Color.argb(0.1f, 0.0f, 0.3f, 0.3f)
            })
    }

    override fun getItemCount(): Int = listDictionary.size

    fun setPrefix(s: String) {
        pref = s
    }
}
