package fr.uparis.mydico

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import fr.uparis.mydico.databinding.WordRowBinding

class AddWordAdapter(private val model: AddWordView) : RecyclerView.Adapter<AddWordAdapter.VH>() {
    var listWords : List<Word> = listOf()

    private var pref: String = ""

    class VH(var binding: WordRowBinding) : RecyclerView.ViewHolder( binding.root ){
        lateinit var word : Word
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = WordRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)


        val holder = VH(binding)
        holder.binding.root.setOnClickListener{
            val word = holder.word
            if (word != model.checkedWords) {
                model.checkedWords = holder.word
            } else {
                model.checkedWords = null
            }
            notifyDataSetChanged()
        }

        return holder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.word = listWords[position]
        with(holder.binding){
            mot.text = holder.word.mot
            src.text = holder.word.language_src
            dest.text = holder.word.language_dest
            link.text=holder.word.http_link
            delete.setOnClickListener{
                model.deleteWord();
            }

            changeColor(holder, position)
        }
        if(pref != ""){
            colorPrefix(holder, pref)
        }


    }

    private fun colorPrefix(holder: VH, prefix: String) {
        holder.binding.mot.text = removePrefix(holder.binding.mot.text as String?, prefix)
        val t = "<font color=#cc0029>$prefix</font><font color=#FF000000>${holder.binding.mot.text}</font>"
        holder.binding.mot.text = HtmlCompat.fromHtml(t, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun removePrefix(s: String?, prefix: String?): CharSequence? {
        return if ((s != null) && (prefix != null) && s.startsWith(prefix)) {
            s.substring(prefix.length)
        } else s
    }

    private fun changeColor(holder: VH, position: Int) {
        holder.binding.root.setBackgroundColor(
            if(holder.word == model.checkedWords){
                Color.argb(0.5f, 0.2f, 0.2f, 0.2f)
            }else if(position % 2 == 0) {
                Color.argb(0.1f, 0.3f, 0.3f, 0.0f)
            }else {
                Color.argb(0.1f, 0.0f, 0.3f, 0.3f)
            })
    }

    override fun getItemCount(): Int = listWords.size

    fun setPrefix(s: String) {
        pref = s
    }


}
