package com.example.tournamentleague.Activity.TournamentFolder

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tournamentleague.API.ApiClient
import com.example.tournamentleague.API.ApiInterface
import com.example.tournamentleague.Adapter.TournamentListAdapter
import com.example.tournamentleague.Model.TournamentListModel
import com.example.tournamentleague.R
import com.example.tournamentleague.databinding.ActivityTournamentListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TournamentListActivity : AppCompatActivity() {

    lateinit var binding : ActivityTournamentListBinding
    lateinit var list : MutableList<TournamentListModel>
    lateinit var apiInterface: ApiInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT))
        binding = ActivityTournamentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tournamentActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,systemBars.bottom)
            insets
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        list = ArrayList()

        var i = intent
        var gameid = i.getIntExtra("gameid",0)
        var gamename = i.getStringExtra("gamename")
        binding.tournamentName.text = gamename.toString()

        getListData(gameid)

    }

    private fun getListData(gameid: Int) {
        val manager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding!!.tournamentListRecyclerView.layoutManager=manager


        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        var call: Call<List<TournamentListModel>> = apiInterface.gettournamentbygameid(gameid)
        call.enqueue(object: Callback<List<TournamentListModel>>
        {
            override fun onResponse(call: Call<List<TournamentListModel>>, response: Response<List<TournamentListModel>>) {
                if (this != null) {
                    list = response.body() as MutableList<TournamentListModel>

                    val adapter = TournamentListAdapter(this@TournamentListActivity, list)
                    binding!!.tournamentListRecyclerView.adapter = adapter
                }

            }

            override fun onFailure(call: Call<List<TournamentListModel>>, t: Throwable) {

                Toast.makeText(this@TournamentListActivity, "No Internet", Toast.LENGTH_LONG).show()
            }
        })
    }
}