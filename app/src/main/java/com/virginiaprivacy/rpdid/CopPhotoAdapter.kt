package com.virginiaprivacy.rpdid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.virginiaprivacy.rpdid.copdata.Cop

class CopPhotoAdapter(cop: Cop) : Adapter<CopPhotoAdapter.ViewHolder>() {

    private val photos = cop.photos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.cop_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photos[position]
        holder.imageView.setImageDrawable(photo.getDrawable())
        holder.imageView.contentDescription = context.getString(R.string.cop_photo_adapter_description_1) + position +
                context.getString(R.string.cop_photo_adapter_description_2) + photos.size
    }

    override fun getItemCount() = photos.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.cop_photo_view)
    }

}