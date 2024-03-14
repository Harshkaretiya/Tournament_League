package com.example.tournamentleague.Activity.TournamentFolder

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tournamentleague.API.ApiClient
import com.example.tournamentleague.API.ApiInterface
import com.example.tournamentleague.Activity.MainActivity
import com.example.tournamentleague.Adapter.TournamentListAdapter
import com.example.tournamentleague.Model.MemberGameDetailModel
import com.example.tournamentleague.Model.ModelUser
import com.example.tournamentleague.Model.TournamentListModel
import com.example.tournamentleague.R
import com.example.tournamentleague.databinding.ActivityTournamentViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TournamentViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityTournamentViewBinding
    lateinit var list : MutableList<TournamentListModel>
    lateinit var apiInterface: ApiInterface
    lateinit var sharedPreferences: SharedPreferences
    var gameid = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT))
        binding = ActivityTournamentViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var i = intent
        var tid = i.getIntExtra("tournamentid",0)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        getTournamentData(tid)

        sharedPreferences = getSharedPreferences("User_Session", Context.MODE_PRIVATE)



    }

    private fun getMemberGameDetail(memberid: Int) {
        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        val call: Call<MemberGameDetailModel> = apiInterface.getmembergamedetailbyid(memberid)
        call.enqueue(object : Callback<MemberGameDetailModel> {
            override fun onResponse(call: Call<MemberGameDetailModel>, response: Response<MemberGameDetailModel>) {
                if (this != null) {
                    if (response.isSuccessful) {
                        var bgmiId = response.body()!!.bgmiid
                        var bgmiName = response.body()!!.bgmiusername

                        setMemberGameDetail(gameid,bgmiId,bgmiName)

                    }
                }
            }

            override fun onFailure(call: Call<MemberGameDetailModel>, t: Throwable) {
                Toast.makeText(applicationContext, "some error occurs", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun setMemberGameDetail(gameid: Int, bgmiId: String, bgmiName: String) {
        //bgmi
        if (gameid == 1){
            if (bgmiId != "")
                binding.gameId.text = bgmiId
            else
                binding.gameId.text = "(Not Set)"

            if (bgmiName != "")
                binding.gameName.text = bgmiName
            else
                binding.gameName.text = "(Not Set)"
        }
    }

    private fun getTournamentData(tournamentId: Int) {
        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        var call: Call<TournamentListModel> = apiInterface.gettournamentbytournamentid(tournamentId)
        call.enqueue(object: Callback<TournamentListModel>
        {
            override fun onResponse(call: Call<TournamentListModel>, response: Response<TournamentListModel>) {
                if (this != null) {
                    if (response.isSuccessful) {
                        binding.totalPrice.text = response.body()!!.totalprice.toString()
                        binding.currentEntries.text = response.body()!!.currententries.toString()
                        binding.maxEntries.text = response.body()!!.maxentries.toString()
                        binding.gameType.text = response.body()!!.gametype
                        binding.dateTime.text = response.body()!!.datetime
                        binding.enterFee.text = response.body()!!.entryfee.toString()
                        gameid = response.body()!!.gameid

                        var memberid = sharedPreferences.getInt("memberid",0)
                        getMemberGameDetail(memberid)
                    }
                }

            }

            override fun onFailure(call: Call<TournamentListModel>, t: Throwable) {

                Toast.makeText(this@TournamentViewActivity, "No Internet", Toast.LENGTH_LONG).show()
            }
        })
    }
}