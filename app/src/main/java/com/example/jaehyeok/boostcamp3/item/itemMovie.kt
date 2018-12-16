package com.example.jaehyeok.boostcamp3.item

data class Homefeed (val items : List<itemMovie>)

data class itemMovie(
    val title : String,
    val link : String,
    val image : String,
    val subtitle : String,
    val pubDate : String,
    val director : String,
    val actor : String,
    val userRating : String
)