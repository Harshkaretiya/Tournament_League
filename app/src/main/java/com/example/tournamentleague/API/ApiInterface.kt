package com.example.tournamentleague.API

import com.example.tournamentleague.Model.HomePageListModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("gethomepagelist.php")
    fun gethomepagelist(): Call<List<HomePageListModel>>
}