package com.example.tournamentleague.API

import com.example.tournamentleague.Model.GameListModel
import com.example.tournamentleague.Model.ModelUser
import com.example.tournamentleague.Model.TournamentListModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @GET("getgamelist.php")
    fun getgamelist(): Call<List<GameListModel>>
    @FormUrlEncoded
    @POST("getmemberbyemail.php")
    fun getmemberbyemail(
        @Field("memberemail") memberemail:String,
        @Field("memberpass") memberpass:String,
    ): Call<ModelUser>

    @FormUrlEncoded
    @POST("insertuser.php")
    fun insertuser(
        @Field("membername") member_name:String,
        @Field("memberemail") member_email:String,
        @Field("memberpass") member_pass:String,
        @Field("memberphone") member_number:String,

    ): Call<Void>

    @GET("gettournamentbygameid.php")
    fun gettournamentbygameid(
        @Query("gameid") game_id:Int,
    ): Call<List<TournamentListModel>>
}