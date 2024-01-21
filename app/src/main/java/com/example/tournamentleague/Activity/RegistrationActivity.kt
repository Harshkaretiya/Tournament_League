package com.example.tournamentleague.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.tournamentleague.API.ApiClient
import com.example.tournamentleague.API.ApiInterface
import com.example.tournamentleague.Model.ModelUser
import com.example.tournamentleague.R
import com.example.tournamentleague.databinding.ActivityRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding
    private lateinit var apiInterface: ApiInterface
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("User_Session", Context.MODE_PRIVATE)

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.registerButton.setOnClickListener {
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()
            val cpass = binding.confirmPassword.text.toString()
            if (verifyInput(email,pass,cpass)) {
                checkEmail(email, pass, false)
            }
        }
    }

    private fun verifyInput(email: String, pass: String, cpass: String): Boolean {
        binding.passwordLayout.helperText = ""
        binding.emailLayout.helperText = ""
        binding.confirmPasswordLayout.helperText = ""

        var working = true

        // remove comments after completion
        /*
        if (!email.matches(Patterns.EMAIL_ADDRESS.toRegex())){
            binding.emailLayout.helperText = "Please enter correct email"
            working = false
        }
        if (pass.length<8){
            binding.passwordLayout.helperText = "Password is weak"
            working = false
        }
        if (cpass != pass || cpass.length<8){
            binding.confirmPasswordLayout.helperText = "Password doesn't match"
            working = false
        }

         */
        return working
    }

    private fun checkEmail(email:String,pass:String,savedetails:Boolean) {
        apiInterface = ApiClient.getapiclient().create(ApiInterface::class.java)
        val call: Call<ModelUser> = apiInterface.getmemberbyemail(email, pass)
        call.enqueue(object : Callback<ModelUser> {
            override fun onResponse(call: Call<ModelUser>, response: Response<ModelUser>) {
                if (this != null) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (savedetails){
                                var edit1: SharedPreferences.Editor = sharedPreferences.edit()

                                edit1.putString("User_Session", "login")

                                edit1.putInt("memberid", response.body()!!.memberid)
                                edit1.putString("membername", response.body()!!.membername)
                                edit1.putString("memberinviteid", response.body()!!.memberinviteid)
                                edit1.putString("memberemail", response.body()!!.memberemail)
                                edit1.putInt("memberphone", response.body()!!.memberphone)
                                edit1.apply()

                                startActivity(Intent(this@RegistrationActivity, MainActivity::class.java))
                            }else {
                                Toast.makeText(this@RegistrationActivity, "Email is already used", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ModelUser>, t: Throwable) {
                createMember(email,pass)
            }
        })
    }

    private fun createMember(email:String,pass:String) {
        val call: Call<Void> = apiInterface.insertuser("",email,pass,"")
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "registered", Toast.LENGTH_LONG).show()
                    checkEmail(email, pass, true)
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(applicationContext,"Fail",Toast.LENGTH_LONG).show()
            }

        })
    }
}