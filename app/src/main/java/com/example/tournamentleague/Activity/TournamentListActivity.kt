package com.example.tournamentleague.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tournamentleague.R
import com.example.tournamentleague.databinding.ActivityMainBinding
import com.example.tournamentleague.databinding.ActivityTournamentListBinding

class TournamentListActivity : AppCompatActivity() {

    lateinit var binding : ActivityTournamentListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTournamentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        
    }
}