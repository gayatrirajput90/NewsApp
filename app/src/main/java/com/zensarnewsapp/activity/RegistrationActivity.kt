package com.zensarnewsapp.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.zensarnewsapp.R
import com.zensarnewsapp.databinding.ActivityRegistrationBinding
import com.zensarnewsapp.utility.Constant
import com.zensarnewsapp.utility.ValidateRegistration

class RegistrationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        auth = FirebaseAuth.getInstance()

        setClickListener()
    }

    fun setClickListener(){

        binding.btnSignup.setOnClickListener {
            email = binding.edtEmail.text.toString()
            password = binding.edtPassword.text.toString()
            name = binding.edtName.text.toString()

            if(email.length!=0 && password.length!=0 && ValidateRegistration.isValidString(email)){
                binding.progressbar.visibility = View.VISIBLE
                createUserEmailAndPassword()}
            else{
                Toast.makeText(this,"Please enter email and password",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if(user!=null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //This function perform firebase authentication
    fun createUserEmailAndPassword(){

        try{
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        binding.progressbar.visibility = View.GONE
                        setSharedPreference(name)
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

    fun setSharedPreference(name : String){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()
        editor.putString(Constant.name,name)
        editor.apply()
        editor.commit()
    }
}