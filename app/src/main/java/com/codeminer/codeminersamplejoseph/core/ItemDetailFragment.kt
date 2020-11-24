package com.codeminer.codeminersamplejoseph.core

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.codeminer.codeminersamplejoseph.R
import com.codeminer.codeminersamplejoseph.models.Movie

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item =
            ItemListActivity.selected_movie
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)
        item?.let {
            rootView.findViewById<TextView>(R.id.item_detail).text = it.title
            rootView.findViewById<TextView>(R.id.item_detail).text = it.title
            rootView.findViewById<TextView>(R.id.item_detail).text = it.title
        }
        return rootView
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}