package com.zensarnewsapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.zensarnewsapp.R

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        if(user!=null) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}