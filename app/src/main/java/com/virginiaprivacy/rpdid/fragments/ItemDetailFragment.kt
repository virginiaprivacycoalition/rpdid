package com.virginiaprivacy.rpdid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.virginiaprivacy.rpdid.CopPhotoAdapter
import com.virginiaprivacy.rpdid.R
import com.virginiaprivacy.rpdid.copdata.Cop
import com.virginiaprivacy.rpdid.cops
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator

class ItemDetailFragment : Fragment() {

    private var pig: Cop? = null

    private lateinit var itemDetailTextView: TextView
    private lateinit var photosRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                pig = cops[it[ARG_ITEM_ID].toString().toInt()]
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_item_detail, container, false)

        rootView.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.let {
            it.title = Cop.getFormattedCopName(pig!!)
            it.isScrollContainer = true
            it.isVerticalScrollBarEnabled  = true
        }

        itemDetailTextView = rootView.findViewById(R.id.item_detail)
        photosRecyclerView = rootView.findViewById(R.id.cop_recycler)

        pig?.let {
            LinearSnapHelper().attachToRecyclerView(photosRecyclerView)
            photosRecyclerView.adapter = CopPhotoAdapter(it)
            photosRecyclerView.layoutManager = LinearLayoutManager(context).apply { orientation = RecyclerView.HORIZONTAL }
            val indicator: ScrollingPagerIndicator = rootView.findViewById(R.id.indicator)
            indicator.attachToRecyclerView(photosRecyclerView)
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}