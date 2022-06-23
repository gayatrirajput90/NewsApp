package com.zensarnewsapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zensarnewsapp.R
import com.zensarnewsapp.databinding.ActivityLoginBinding


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

    fun setClickListener(){
        binding.btnLogin.setOnClickListener {
            email = binding.edtEmail.text.toString()
            password = binding.edtPassword.text.toString()

            if(email.length!=0 && password.length!=0){
                binding.progressbar.visibility = View.VISIBLE
                signinUserEmailAndPassword()}
            else{
                Toast.makeText(this,"Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signinUserEmailAndPassword(){

        try{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        binding.progressbar.visibility = View.GONE
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }

                    else {
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