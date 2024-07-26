package com.example.openinapptest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var linksAdapter: LinksAdapter
    private lateinit var topLinks: List<Link>
    private lateinit var recentLinks: List<Link>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        linksAdapter = LinksAdapter(emptyList())
        findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = linksAdapter
        }

        CoroutineScope(Dispatchers.Main).launch{
            getGreeting()
            fetchDashboardData()
        }
    }

    private fun getGreeting() {
        CoroutineScope(Dispatchers.Main).launch {
            val calender = Calendar.getInstance()
            val hourOfDay = calender.get(Calendar.HOUR_OF_DAY)

            when {
                hourOfDay in 0..11 -> findViewById<TextView>(R.id.greeting).text = "Good Morning"
                hourOfDay in 12..17 -> findViewById<TextView>(R.id.greeting).text = "Good Afternoon"
                else -> findViewById<TextView>(R.id.greeting).text = "Good Evening"
            }
        }
    }

    private fun fetchDashboardData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.apiService.getDashboardData()
                Log.d("Api Response", "$response")
                withContext(Dispatchers.Main) {
                    updateUI(response)
                    topLinks = response.data.topLinks
                    recentLinks = response.data.recentLinks
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(response: ApiResponse) {
        findViewById<TextView>(R.id.todaysClicks).text = response.todayClicks.toString()
        findViewById<TextView>(R.id.topLocation).text = response.topLocation
        findViewById<TextView>(R.id.topSource).text = response.topSource
    }

    fun onTopLinksButtonClicked(view: View) {
        CoroutineScope(Dispatchers.Main).launch {
            findViewById<Button>(R.id.topLinksButton).apply {
                setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.Blue))
                setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
            }
            findViewById<Button>(R.id.recentLinksButton).apply {
                setBackgroundColor(ContextCompat.getColor(this@MainActivity, com.google.android.material.R.color.design_default_color_background))
                setTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))
            }
            linksAdapter.updateLinks(topLinks)
        }
    }

    fun onRecentLinkButtonClicked(view: View) {
        CoroutineScope(Dispatchers.Main).launch {
            findViewById<Button>(R.id.topLinksButton).apply {
                setBackgroundColor(ContextCompat.getColor(this@MainActivity, com.google.android.material.R.color.design_default_color_background))
                setTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))
            }
            findViewById<Button>(R.id.recentLinksButton).apply {
                setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.Blue))
                setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
            }
            linksAdapter.updateLinks(recentLinks)
        }
    }
}