package com.codeminer.codeminersamplejoseph.core

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)
        item?.let {
            rootView.findViewById<TextView>(R.id.det_episode).text = "Episode "+it.episode_id.toString()
            rootView.findViewById<TextView>(R.id.det_title).text = it.title
            rootView.findViewById<TextView>(R.id.det_opening).text = it.opening_crawl
            rootView.findViewById<TextView>(R.id.det_director).text = it.director
            rootView.findViewById<TextView>(R.id.det_producer).text = it.producer
            val dynamicUrl = it.url
            val linkedText = String.format("<a href=\"%s\">%s</a> ",dynamicUrl, dynamicUrl)
            rootView.findViewById<TextView>(R.id.det_url).text = Html.fromHtml(linkedText)
            rootView.findViewById<TextView>(R.id.det_url).movementMethod = LinkMovementMethod.getInstance()
        }
        return rootView
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}