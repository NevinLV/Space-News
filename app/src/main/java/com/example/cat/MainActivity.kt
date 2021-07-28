package com.example.cat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.solver.widgets.Rectangle
import androidx.core.app.ActivityCompat.recreate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cat.api.News
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.RecursiveAction

private var titlesList = mutableListOf<String>()
private var summaryList = mutableListOf<String>()
private var dateList = mutableListOf<String>()
private var newsSiteList = mutableListOf<String>()

const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"
val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        getCurrentData(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private fun addToList(title: String, summary: String, date: String, newsSite: String){
        titlesList.add(title)
        summaryList.add(summary)
        dateList.add(date)
        newsSiteList.add(newsSite)
    }

    private fun postToList(data: News){
        for(i in 0..4){
            addToList(data[i].title, data[i].summary, data[i].updatedAt.substring(0, 10), data[i].newsSite)
        }
    }

    private fun getCurrentData(m: MainActivity)
    {

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getSpaceNews().awaitResponse()
                if (response.isSuccessful) {

                    val data = response.body()!!
                    Log.d(TAG, data.toString())

                    withContext(Dispatchers.Main) {
                        postToList(data)
                        rv_NewsList.layoutManager = LinearLayoutManager(m)
                        rv_NewsList.adapter = RecyclerAdapter(titlesList, summaryList, dateList, newsSiteList)
                    }

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    Toast.makeText(
                        applicationContext,
                        "Seems like something went wrong...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

}


