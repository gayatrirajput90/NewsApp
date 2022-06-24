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
import com.zensarnewsapp.databinding.ActivityRegistrationBinding
import com.zensarnewsapp.utility.Constant
import com.zensarnewsapp.utility.Utility
import com.zensarnewsapp.utility.ValidateRegistration

class RegistrationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        auth = FirebaseAuth.getInstance()
        setClickListener()
    }

    fun setClickListener() {

        binding.btnSignup.setOnClickListener {
            email = binding.edtEmail.text.toString()
            password = binding.edtPassword.text.toString()
            name = binding.edtName.text.toString()

            if(Utility.checkForInternet(applicationContext)){
                if(validate()){
                    createUserEmailAndPassword()
                }
            } else{
                Toast.makeText(this,getString(R.string.not_internet),Toast.LENGTH_SHORT).show()
            }

        }

        binding.btLogin.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

    }

    fun validate (): Boolean {

        if(name.length==0){
            Toast.makeText(this,getString(R.string.please_enter_name),Toast.LENGTH_SHORT).show()
            return false
        } else if (email.length==0){
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

    //This function perform firebase authentication for creating the new user
    fun createUserEmailAndPassword(){

        try{
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        binding.progressbar.visibility = View.GONE
                        Utility.setSharedPrefernce(this,Constant.name,name)
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }

                    else {
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        binding.progressbar.visibility = View.GONE
                    }
                }
        } catch (e: Exception){
            Log.d("Auth Error","${e.message}")
        }

    }
}