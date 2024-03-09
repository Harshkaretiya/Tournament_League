package com.example.tournamentleague.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GameListModel {
    @Expose
    @SerializedName("gameid")
    var gameid = 0

    @Expose
    @SerializedName("name")
    var name = ""

    @Expose
    @SerializedName("image")
    var image = ""

    @Expose
    @SerializedName("gametype")
    var gametype = ""
}