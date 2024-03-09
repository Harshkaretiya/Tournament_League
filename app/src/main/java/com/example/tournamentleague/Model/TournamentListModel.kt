package com.example.tournamentleague.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TournamentListModel {
    @Expose
    @SerializedName("tournamentid")
    var tournamentid = 0

    @Expose
    @SerializedName("gameid")
    var gameid = 0

    @Expose
    @SerializedName("currententries")
    var currententries = 0

    @Expose
    @SerializedName("maxentries")
    var maxentries = 0

    @Expose
    @SerializedName("totalprice")
    var totalprice = 0

    @Expose
    @SerializedName("entryfee")
    var entryfee = 0

    @Expose
    @SerializedName("gametype")
    var gametype = ""

    @Expose
    @SerializedName("datetime")
    var datetime = ""


}