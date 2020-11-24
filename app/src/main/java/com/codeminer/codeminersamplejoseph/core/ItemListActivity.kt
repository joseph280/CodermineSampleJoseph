package com.codeminer.codeminersamplejoseph.core

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.codeminer.codeminersamplejoseph.R
import com.codeminer.codeminersamplejoseph.models.Movie
import com.codeminer.codeminersamplejoseph.models.MovieList
import com.codeminer.codeminersamplejoseph.services.ControllerSW
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ItemListActivity : AppCompatActivity() {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private var movieList: MovieList? = MovieList()
    private lateinit var recyclerView: RecyclerView
    private var fav_film: Int = 0
    private var film_image: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val sharedPref = getSharedPreferences("USER_PREF",Context.MODE_PRIVATE)
        fav_film = sharedPref.getInt(getString(R.string.favorite), 0)
        Log.e("FAV", "Wrong fav "+fav_film.toString())

        recyclerView = findViewById(R.id.item_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title
        toolbar.changeToolbarFont()
        val controller = ControllerSW()
        controller.start(this)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Codermine is awesome!", Snackbar.LENGTH_LONG)
                    .setAction("Codermine is awesome!", null).show()
        }

        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            twoPane = true
        }

    }

    fun onAPIresponse(ml: MovieList? ){
        movieList = ml
        recyclerView.adapter =
            movieList?.results?.let {
                SimpleItemRecyclerViewAdapter(
                    this,
                    it,
                    twoPane,
                    fav_film,
                    film_image
                )
            }
    }

    class SimpleItemRecyclerViewAdapter(private val parentActivity: ItemListActivity,
                                        private val values: List<Movie>,
                                        private val twoPane: Boolean,
                                        private val the_fav_film: Int,
                                        private var film_image: Int):
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as Movie
                selected_movie = item
                when (item.episode_id) {
                    1 -> film_image = R.drawable.im_1
                    2 -> film_image =R.drawable.im_2
                    3 -> film_image =R.drawable.im_3
                    4 -> film_image =R.drawable.im_4
                    5 -> film_image =R.drawable.im_5
                    6 -> film_image =R.drawable.im_6
                    else -> { // Note the block
                        film_image =R.drawable.empty_view
                    }
                }
                selected_image = film_image
                if (twoPane) {
                    val fragment = ItemDetailFragment()
                        .apply {
                        arguments = Bundle().apply {
                            putInt(ItemDetailFragment.ARG_ITEM_ID, item.episode_id)
                        }
                    }
                    parentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java)
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_adapter_layout, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.title
            holder.contentView.text = item.director
            if (the_fav_film == item.episode_id){
                holder.ic_image.setImageResource(R.drawable.heart_on)
            }
            when (item.episode_id) {
                1 -> film_image = R.drawable.im_1
                2 -> film_image =R.drawable.im_2
                3 -> film_image =R.drawable.im_3
                4 -> film_image =R.drawable.im_4
                5 -> film_image =R.drawable.im_5
                6 -> film_image =R.drawable.im_6
                else -> { // Note the block
                    film_image =R.drawable.empty_view
                }
            }
            holder.image.setImageResource(film_image)
            selected_image = film_image
            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.findViewById(R.id.title)
            val contentView: TextView = view.findViewById(R.id.content)
            val image: ImageView = view.findViewById(R.id.image)
            val ic_image: ImageView = view.findViewById(R.id.favorite)
        }
    }

    fun Toolbar.changeToolbarFont(){
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is TextView && view.text == title) {
                view.typeface = Typeface.createFromAsset(view.context.assets, "fonts/starjedi.ttf")
                break
            }
        }
    }

    companion object {
        var selected_movie: Movie? = null
        var selected_image: Int? = null
    }
}