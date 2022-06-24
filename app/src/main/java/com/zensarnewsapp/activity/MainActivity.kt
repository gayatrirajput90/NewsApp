package com.zensarnewsapp.activity

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.zensarnewsapp.R
import com.zensarnewsapp.adapter.NewsAdapter
import com.zensarnewsapp.apiinterface.ItemClickListener
import com.zensarnewsapp.databinding.ActivityMainBinding
import com.zensarnewsapp.repository.NewsRepository
import com.zensarnewsapp.utility.Constant
import com.zensarnewsapp.utility.Utility
import com.zensarnewsapp.viewmodel.NewsViemodelfactory
import com.zensarnewsapp.viewmodel.NewsViewmodel

class MainActivity : AppCompatActivity(),ItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: NewsViewmodel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private  var countryName: String = "us"
    private lateinit var name: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        initialization()
        initializSharedPrefence()
        setUsernameToToolbar()
        checkInternetConnectivity()
        setListener()
    }

    // This function checks the internet connectivity
    fun checkInternetConnectivity() {
            if(Utility.checkForInternet(applicationContext)){

                binding.progressBar.visibility = View.VISIBLE
                binding.rvNews.visibility = View.VISIBLE
                if (countryName==null) {
                    //setSharedPreference("us")
                    countryName = "us"
                    viewmodel.getAllNews(countryName,Constant.api_key)
                } else {
                    getSharedPreference()
                    viewmodel.getAllNews(countryName,Constant.api_key)
                }
                setObserver()
            } else{
                binding.tvNotFound.visibility = View.VISIBLE
                binding.tvNotFound.text = getString(R.string.not_internet)
                binding.rvNews.visibility = View.GONE
        }
    }

    //This function initializes the sharedprefrence for storing the selected country
    fun initializSharedPrefence(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()
    }

    fun setUsernameToToolbar() {
        name = Utility.getSharedPrefernce(this,Constant.name)
        binding.tvName.text = "Welcome "+name
    }

    //This function initilizes the ViewModelFactory and adapter
    fun initialization() {
        val repository = NewsRepository()
        val viewModelFactory = NewsViemodelfactory(repository)
        viewmodel = ViewModelProvider(this, viewModelFactory)[NewsViewmodel::class.java]
        newsAdapter = NewsAdapter(this)
    }

    //This function observe the livedate and set into the recyclerview
    fun setObserver() {
        viewmodel.newsLiveData.observe(this, Observer {

            if(it.articles.size!=0) {
                binding.rvNews.visibility = View.VISIBLE
                binding.tvNotFound.visibility = View.GONE
                newsAdapter.setData(it.articles)
                binding.rvNews.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = newsAdapter
                }
            } else {
                binding.rvNews.visibility = View.GONE
                binding.tvNotFound.visibility = View.VISIBLE
            }
            binding.progressBar.visibility = View.GONE
        })
    }

    //This function select the country for fetching the news to the corresponding country
    fun setListener(){

        binding.rg.setOnCheckedChangeListener{ group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)

            if(radio.text.toString().equals("USA")){
                setSharedPreference("us")
            } else{
                setSharedPreference("ca")
            }
            checkInternetConnectivity()
        }

        binding.ivLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // This function set the key/value pair in shared preference
    fun setSharedPreference(country : String){
        editor.putString(getString(R.string.country),country)
        editor.apply()
        editor.commit()
    }

    //This function gets the value from shared preference
    fun getSharedPreference(){
         countryName = sharedPreferences.getString(getString(R.string.country), "us").toString()

        if(countryName.equals("us",true)) {
            binding.rdUs.isChecked = true
        } else{
            binding.rdCanada.isChecked = true
        }
    }

    //This fun is used to navigate the user to the browser when user clicks on  particular news cell
    override fun onItemClickListener(url: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        startActivity(openURL)
    }
}