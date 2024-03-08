package com.example.tournamentleague.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tournamentleague.API.ApiClient
import com.example.tournamentleague.API.ApiInterface
import com.example.tournamentleague.Activity.MainActivity
import com.example.tournamentleague.Adapter.HomePageListAdapter
import com.example.tournamentleague.Model.HomePageListModel
import com.example.tournamentleague.R
import com.example.tournamentleague.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    lateinit var list : MutableList<HomePageListModel>
    lateinit var apiInterface: ApiInterface
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding!!.root

        binding!!.drawerButton.setOnClickListener {
            if (activity is MainActivity) {
                val mainActivity = activity as MainActivity
                mainActivity.openDrawer()
            }
        }


        var manager : RecyclerView.LayoutManager = LinearLayoutManager(requireActivity())
        binding!!.recyclerList.layoutManager=manager

        list = ArrayList()

        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        var call: Call<List<HomePageListModel>> = apiInterface.gethomepagelist()
        call.enqueue(object: Callback<List<HomePageListModel>>
        {
            override fun onResponse(call: Call<List<HomePageListModel>>, response: Response<List<HomePageListModel>>) {
                if (context != null) {
                    list = response.body() as MutableList<HomePageListModel>

                    var adapter = HomePageListAdapter(requireActivity(), list)
                    binding!!.recyclerList.adapter = adapter
                }

            }

            override fun onFailure(call: Call<List<HomePageListModel>>, t: Throwable) {
                Log.e("API Error", "Failed to make API call", t)
                Toast.makeText(requireActivity(), "No Internet", Toast.LENGTH_LONG).show()
            }
        })

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}