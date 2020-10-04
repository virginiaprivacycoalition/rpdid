package com.virginiaprivacy.rpdid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.virginiaprivacy.rpdid.CopAdapter
import com.virginiaprivacy.rpdid.CopLoader
import com.virginiaprivacy.rpdid.R
import com.virginiaprivacy.rpdid.copdata.Cop
import com.virginiaprivacy.rpdid.cops

class ItemListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.item_list)
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

        val onClickListener = View.OnClickListener { itemView ->
            val cop = itemView.tag as Cop
            val bundle = Bundle()
            bundle.putString(
                ItemDetailFragment.ARG_ITEM_ID,
                cops.values.first { it.id == cop.id }.id.toString())

            if (itemDetailFragmentContainer != null) {
                with(itemDetailFragmentContainer) {
                    findNavController()
                            .navigate(R.id.fragment_item_detail, bundle)
                }
            } else {
                itemView.findNavController().navigate(R.id.show_item_detail, bundle)
            }
        }

        setupRecyclerView(recyclerView, onClickListener)
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        onClickListener: View.OnClickListener
    ) {

        recyclerView.adapter = CopAdapter(CopLoader().allCops() as List<Cop>, onClickListener)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

}