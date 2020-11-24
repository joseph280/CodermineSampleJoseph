package com.codeminer.codeminersamplejoseph.services

import com.google.gson.annotations.SerializedName

class movieResponse {

    @SerializedName("title")
    var title: String? = null

    @SerializedName("episode_id")
    var episode_id: Int? = 0

    @SerializedName("opening_crawl")
    var opening_crawl: String? = null

    @SerializedName("director")
    var director: String? = null

    @SerializedName("producer")
    var producer: String? = null

    @SerializedName("release_date")
    var release_date: String? = null
}