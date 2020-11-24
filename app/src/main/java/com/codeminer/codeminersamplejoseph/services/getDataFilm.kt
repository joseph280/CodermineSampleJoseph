package com.codeminer.codeminersamplejoseph.services

import android.util.Log
import com.codeminer.codeminersamplejoseph.core.ItemListActivity
import com.codeminer.codeminersamplejoseph.models.MovieList
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface StarWarsAPI {
    @GET("films/")
    fun loadChanges(): Call<MovieList?>?
}

class ControllerSW : Callback<MovieList?> {
    var changesList: MovieList? = MovieList()
    var activity: ItemListActivity? = null

    fun start(a: ItemListActivity?) {
        activity = a
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val starWarsAPI: StarWarsAPI = retrofit.create<StarWarsAPI>(StarWarsAPI::class.java)
        starWarsAPI.loadChanges()?.enqueue(this)
    }

    override fun onResponse(
        call: Call<MovieList?>?,
        response: Response<MovieList?>
    ) {
        Log.e("HOLA", "WE DID IT")
        Log.e("HOLA", "Success $response")
        if (response.isSuccessful()) {
            //Log.e("HOLA", ControllerSW.toString())
            changesList = response.body()
            Log.e("HOLA", changesList!!.results[0].title.toString())
            activity?.onAPIresponse(changesList)
        } else {
            println(response.errorBody())
        }
    }

    override fun onFailure(
        call: Call<MovieList?>?,
        t: Throwable
    ) {
        t.printStackTrace()
    }

    companion object {
        const val BASE_URL = "https://swapi.dev/api/"
    }
}