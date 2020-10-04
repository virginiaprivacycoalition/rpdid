package com.virginiaprivacy.rpdid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.virginiaprivacy.rpdid.copdata.Cop
import kotlinx.coroutines.*

class CopAdapter(
    private val pigs: List<Cop>,
    private var clickListener: View.OnClickListener? = null
) : RecyclerView.Adapter<CopAdapter.ViewHolder>() {

    init {
        cops.putAll(pigs.associateBy { it.id })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cop = pigs[position]
        holder.copNameView.text = Cop.getFormattedCopName(cop)
        bgScope.launch {
            setImage(holder, cop)
        }
        holder.copImageView.contentDescription =
            context.getString(R.string.cop_list_image_content_1) + Cop.getFormattedCopName(cop) + context.getString(
                            R.string.cop_list_image_content_2)
        holder.itemView.tag = cop
        clickListener?.let {
            holder.copCardView.tag = cop
            holder.copImageView.tag = cop
            holder.copNameView.tag = cop
            holder.itemView.tag = cop
            holder.copCardView.setOnClickListener(it)
        }
    }

    private fun setImage(holder: ViewHolder, cop: Cop) {
        uiScope.launch {
            holder.copImageView.setImageDrawable(cop.photos.first().getDrawable())
        }
    }

    override fun getItemCount(): Int = pigs.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val copNameView: TextView = view.findViewById(R.id.id_text)
        val copImageView: ImageView = view.findViewById(R.id.main_cop_photo)
        val copCardView: CardView = view.findViewById(R.id.cop_card_view)
    }

}

val uiScope = MainScope()
val bgScope = CoroutineScope(Dispatchers.IO)