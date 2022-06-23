package com.zensarnewsapp.activity

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zensarnewsapp.R
import com.zensarnewsapp.adapter.NewsAdapter
import com.zensarnewsapp.apiinterface.ItemClickListener
import com.zensarnewsapp.databinding.ActivityMainBinding
import com.zensarnewsapp.repository.NewsRepository
import com.zensarnewsapp.utility.Constant
import com.zensarnewsapp.utility.Utility
import com.zensarnewsapp.viewmodel.NewsViemodelfactory
import com.zensarnewsapp.viewmodel.NewsViewmodel
import java.util.prefs.Preferences

class MainActivity : AppCompatActivity(),ItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: NewsViewmodel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var countryName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initialization()
        initializSharedPrefence()
        checkInternetConnectivity()
        setListener()
    }

    // This function checks the internet connectivity
    fun checkInternetConnectivity() {

        if(Utility.checkForInternet(applicationContext)){
            binding.progressBar.visibility = View.VISIBLE
            getSharedPreference()
            viewmodel.getAllNews(countryName,Constant.api_key)
        }
        else{
            binding.tvNotFound.visibility = View.VISIBLE
            binding.tvNotFound.text = "No Internet"
        }

        setObserver()
    }

    fun initializSharedPrefence(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()
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
            }

            else {
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
            setSharedPreference(radio.text.toString())
            checkInternetConnectivity()
        }
    }

    fun setSharedPreference(country : String){
        editor.putString(getString(R.string.country),country)
        editor.apply()
        editor.commit()
    }

    fun getSharedPreference(){
         countryName = sharedPreferences.getString(getString(R.string.country), "").toString()
    }

    override fun onItemClickListener(url: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        startActivity(openURL)
    }

}