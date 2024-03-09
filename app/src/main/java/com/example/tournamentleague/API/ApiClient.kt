package com.example.tournamentleague.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ApiClient {
    companion object {
        var BASE_URL = "https://harshkaretiya.000webhostapp.com/TournamentLeague/"
//        var BASE_URL = "https://www.harshkaretiya.rf.gd/"

        var retrofit: Retrofit? = null


        fun getapiclient(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }



            return retrofit!!

        }
    }
}