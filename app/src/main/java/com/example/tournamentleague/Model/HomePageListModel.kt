package com.example.tournamentleague.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HomePageListModel {
    @Expose
    @SerializedName("tid")
    var tid = 0

    @Expose
    @SerializedName("name")
    var name = ""

    @Expose
    @SerializedName("image")
    var image = ""

    @Expose
    @SerializedName("description")
    var description = ""
}