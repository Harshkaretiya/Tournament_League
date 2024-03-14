package com.example.tournamentleague.Activity.LoginAndRegistration

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tournamentleague.API.ApiClient
import com.example.tournamentleague.API.ApiInterface
import com.example.tournamentleague.Activity.MainActivity
import com.example.tournamentleague.Model.ModelUser
import com.example.tournamentleague.R
import com.example.tournamentleague.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private lateinit var apiInterface: ApiInterface
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT))
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,systemBars.bottom)
            insets
        }


        sharedPreferences = getSharedPreferences("User_Session", Context.MODE_PRIVATE)

        if (sharedPreferences.getString("User_Session","notworking") == "login") {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            verifyLogin()
        }
        binding.loginGuest.setOnClickListener {
            var edit1: SharedPreferences.Editor = sharedPreferences.edit()

            edit1.putString("User_Session", "guest")
            edit1.apply()
        }
        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

    }

    private fun verifyLogin() {

        val email = binding.email.text.toString()
        val pass = binding.password.text.toString()

        if (verifyInput(email,pass)) {
            apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
            val call: Call<ModelUser> = apiInterface.getmemberbyemail(email, pass)
            call.enqueue(object : Callback<ModelUser> {
                override fun onResponse(call: Call<ModelUser>, response: Response<ModelUser>) {
                    if (this != null) {
                        if (response.isSuccessful) {
                            var edit1: SharedPreferences.Editor = sharedPreferences.edit()

                            edit1.putString("User_Session", "login")

                            edit1.putInt("memberid", response.body()!!.memberid)
                            edit1.putString("membername", response.body()!!.membername)
                            edit1.putString("memberinviteid", response.body()!!.memberinviteid)
                            edit1.putString("memberemail", response.body()!!.memberemail)
                            edit1.putInt("memberphone", response.body()!!.memberphone)
                            edit1.apply()

                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }
                    }

                }

                override fun onFailure(call: Call<ModelUser>, t: Throwable) {
                    Toast.makeText(applicationContext, "some error occurs", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
    }

    private fun verifyInput(email:String,pass:String): Boolean {
        var working = true
        binding.emailLayout.helperText = ""
        binding.passwordLayout.helperText = ""
        //remove after complete
        /*
        if (!email.matches(Patterns.EMAIL_ADDRESS.toRegex())){
            binding.emailLayout.helperText = "Please enter correct email"
            working = false
        }
        if (pass.length<8){
            binding.passwordLayout.helperText = "Password should be atleast 8 digit long"
            working = false
        }
        */

        return working
    }
}