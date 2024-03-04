package com.example.tournamentleague.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tournamentleague.R
import com.example.tournamentleague.databinding.FragmentNotificationBinding
import com.example.tournamentleague.databinding.FragmentRecentMatchBinding

class RecentMatchFragment : Fragment() {

    private var _binding: FragmentRecentMatchBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentMatchBinding.inflate(inflater, container, false)
        val view = binding!!.root

        // Inflate the layout for this fragment
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}