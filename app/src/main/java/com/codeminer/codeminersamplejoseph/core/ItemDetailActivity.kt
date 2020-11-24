package com.codeminer.codeminersamplejoseph.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.codeminer.codeminersamplejoseph.R

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ItemListActivity].
 */
class ItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(findViewById(R.id.detail_toolbar))

        findViewById<FloatingActionButton>(R.id.fab_detail).setOnClickListener { view ->

            Snackbar.make(view, "Favorite film saved!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            findViewById<FloatingActionButton>(R.id.fab_detail).setImageResource(R.drawable.heart_on)

            val sharedPref = getSharedPreferences("USER_PREF",Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putInt(getString(R.string.favorite), ItemListActivity.selected_movie?.episode_id!!)
            editor.apply()
            Log.e("FAV", "The write "+ItemListActivity.selected_movie?.episode_id.toString())

        }
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            val fragment = ItemDetailFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(
                        ItemDetailFragment.ARG_ITEM_ID,
                            intent.getStringExtra(ItemDetailFragment.ARG_ITEM_ID))
                }
            }
            supportFragmentManager.beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit()
        }
        ItemListActivity.selected_image?.let { it1 ->
            findViewById<CoordinatorLayout>(R.id.full_layout).setBackgroundResource(
                it1
            )
        }
        this.title = ItemListActivity.selected_movie?.title;
        val sharedPref = getSharedPreferences("USER_PREF",Context.MODE_PRIVATE)
        val fav_film = sharedPref.getInt(getString(R.string.favorite), 0)
        if (fav_film == ItemListActivity.selected_movie?.episode_id){
            findViewById<FloatingActionButton>(R.id.fab_detail).setImageResource(R.drawable.heart_on)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =

            when (item.itemId) {
                android.R.id.home -> {
                    navigateUpTo(Intent(this, ItemListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }


}