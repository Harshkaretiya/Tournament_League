package com.example.tournamentleague.Activity.TournamentFolder

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tournamentleague.API.ApiClient
import com.example.tournamentleague.API.ApiInterface
import com.example.tournamentleague.Adapter.InviteTeamMemberAdapter
import com.example.tournamentleague.Model.FriendInviteModel
import com.example.tournamentleague.Model.MemberGameDetailModel
import com.example.tournamentleague.Model.TournamentListModel
import com.example.tournamentleague.R
import com.example.tournamentleague.databinding.ActivityTournamentViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TournamentViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityTournamentViewBinding
    lateinit var apiInterface: ApiInterface
    lateinit var sharedPreferences: SharedPreferences
    var bottomSheetDialog : BottomSheetDialog? = null
    var gameid = 0
    lateinit var list : MutableList<FriendInviteModel>
    private val selectedItemsList = mutableListOf<FriendInviteModel>()
    lateinit var inviteTeamMemberAdapter: InviteTeamMemberAdapter
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

        sharedPreferences = getSharedPreferences("User_Session", Context.MODE_PRIVATE)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.inviteTeamMember.setOnClickListener {
            showInviteTeamMemberDialog()
        }

        getTournamentData(tid)
        setInviteTeamMemberDialog()

    }

    @SuppressLint("MissingInflatedId")
    private fun setInviteTeamMemberDialog() {
        bottomSheetDialog = BottomSheetDialog(this,R.style.myBottomSheet)
        val view = layoutInflater.inflate(R.layout.design_invite_team_member_dialog, null)
        var layout = view.findViewById<RecyclerView>(R.id.friendRecyclerList)
        var addMemberButton = view.findViewById<MaterialCardView>(R.id.addMember)

        addMemberButton.setOnClickListener {
            addSelectedItemsToList()
        }

        bottomSheetDialog!!.setContentView(view)
        setFriendData(layout)
    }


    private fun addSelectedItemsToList() {
        selectedItemsList.clear()
        selectedItemsList.addAll(inviteTeamMemberAdapter.getSelectedItems())

        binding.gameId.text = "(Not Set)"
        binding.gameName.text = "(Not Set)"
        binding.Friend1Name.text = "(Add Member)"
        binding.Friend2Name.text = "(Add Member)"
        binding.Friend3Name.text = "(Add Member)"
        binding.Friend4Name.text = "(Add Member)"

        binding.Friend1GameId.text = "(Not Set)"
        binding.Friend1Username.text = "(Not Set)"
        binding.Friend2GameId.text = "(Not Set)"
        binding.Friend2Username.text = "(Not Set)"
        binding.Friend3GameId.text = "(Not Set)"
        binding.Friend3Username.text = "(Not Set)"
        binding.Friend4GameId.text = "(Not Set)"
        binding.Friend4Username.text = "(Not Set)"

        selectedItemsList.forEachIndexed { index, item ->
            val friendNo = index + 1
            getMemberGameDetail(item.friendmemberid, friendNo)
            when (friendNo) {
                1 -> binding.Friend1Name.text = item.friendname
                2 -> binding.Friend2Name.text = item.friendname
                3 -> binding.Friend3Name.text = item.friendname
                4 -> binding.Friend4Name.text = item.friendname
            }
        }
        bottomSheetDialog!!.dismiss()
    }

    private fun getMemberGameDetail(memberid: Int,FriendNo: Int = 0) {
        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        val call: Call<MemberGameDetailModel> = apiInterface.getmembergamedetailbyid(memberid)
        call.enqueue(object : Callback<MemberGameDetailModel> {
            override fun onResponse(call: Call<MemberGameDetailModel>, response: Response<MemberGameDetailModel>) {
                if (this != null) {
                    if (response.isSuccessful) {
                        val bgmiId = response.body()?.bgmiid ?: ""
                        val bgmiName = response.body()?.bgmiusername ?: ""
                        setMemberGameDetail(gameid, bgmiId, bgmiName, FriendNo)
                    }
                }
            }

            override fun onFailure(call: Call<MemberGameDetailModel>, t: Throwable) {
                Toast.makeText(applicationContext, "some error occurs", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun setMemberGameDetail(gameid: Int, bgmiId: String, bgmiName: String, FriendNo: Int = 0) {
        if (gameid == 1) {
            // Ensure to iterate only up to the number of selected items or up to 4, whichever is smaller
            if (FriendNo == 0) {
                    if (bgmiId != "") binding.gameId.text = bgmiId
                    if (bgmiName != "") binding.gameName.text = bgmiName
            }
            if (FriendNo == 1) {
                    if (bgmiId != "") binding.Friend1GameId.text = bgmiId
                    if (bgmiName != "") binding.Friend1Username.text = bgmiName
            }
            if (FriendNo == 2) {
                    if (bgmiId != "") binding.Friend2GameId.text = bgmiId
                    if (bgmiName != "") binding.Friend2Username.text = bgmiName
            }
            if (FriendNo == 3) {
                    if (bgmiId != "") binding.Friend3GameId.text = bgmiId
                    if (bgmiName != "") binding.Friend3Username.text = bgmiName
            }
            if (FriendNo == 4) {
                    if (bgmiId != "") binding.Friend4GameId.text = bgmiId
                    if (bgmiName != "") binding.Friend4Username.text = bgmiName
            }

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

                        var entryFee = response.body()!!.entryfee
                        if (entryFee == 0)
                            binding.enterFee.text = "Free"
                        else
                            binding.enterFee.text = entryFee.toString()
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

    private fun showInviteTeamMemberDialog(){
        bottomSheetDialog!!.show()
    }

    private fun setFriendData(layout: RecyclerView?) {
        val manager: RecyclerView.LayoutManager = GridLayoutManager(this,3)
        layout!!.layoutManager = manager


        list = ArrayList()
        var memberid = sharedPreferences.getInt("memberid",0)

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        var call: Call<List<FriendInviteModel>> = apiInterface.getfriendlistbymemberid(memberid)
        call.enqueue(object: Callback<List<FriendInviteModel>>
        {
            override fun onResponse(call: Call<List<FriendInviteModel>>, response: Response<List<FriendInviteModel>>) {
                if (this != null) {
                    list = response.body() as MutableList<FriendInviteModel>

                    inviteTeamMemberAdapter  = InviteTeamMemberAdapter(this@TournamentViewActivity, list)
                    layout!!.adapter = inviteTeamMemberAdapter
                }

            }

            override fun onFailure(call: Call<List<FriendInviteModel>>, t: Throwable) {

                Toast.makeText(this@TournamentViewActivity, "No Internet", Toast.LENGTH_LONG).show()
            }
        })
    }
}