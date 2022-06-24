package com.zensarnewsapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.zensarnewsapp.R
import com.zensarnewsapp.databinding.ActivityLoginBinding
import com.zensarnewsapp.utility.Utility
import com.zensarnewsapp.utility.ValidateRegistration


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        setClickListener()
    }

    fun setClickListener() {
        binding.btnLogin.setOnClickListener {
            email = binding.edtEmail.text.toString()
            password = binding.edtPassword.text.toString()

            if(Utility.checkForInternet(applicationContext)){
                if(validate()){
                    binding.progressbar.visibility = View.VISIBLE
                    signinUserEmailAndPassword()
                }
            } else{
                Toast.makeText(this,getString(R.string.not_internet),Toast.LENGTH_SHORT).show()
            }
        }

        binding.btSignup.setOnClickListener{
            val intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    fun validate (): Boolean {

      if (email.length==0){
            Toast.makeText(this,getString(R.string.please_enter_email),Toast.LENGTH_SHORT).show()
            return false
        } else if (!ValidateRegistration.isValidString(email)){
            Toast.makeText(this,getString(R.string.please_enter_valid_email),Toast.LENGTH_SHORT).show()
            return false
        } else if(password.length==0) {
            Toast.makeText(this,getString(R.string.please_enter_password),Toast.LENGTH_SHORT).show()
            return false
        }else if(password.length<6){
            Toast.makeText(this,getString(R.string.please_enter_valid_password),Toast.LENGTH_SHORT).show()
            return false
        }else{
            return true
        }
    }

    fun signinUserEmailAndPassword(){

        try{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        binding.progressbar.visibility = View.GONE
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        binding.progressbar.visibility = View.GONE
                    }
                }
        }
        catch (e: Exception){
            Log.d("Auth Error","${e.message}")
        }
    }
}