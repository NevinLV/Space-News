package com.example.cat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cat.api.SpaceNewsJson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.spaceflightnewsapi.net/v3"

class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //getCurrentData()

        News.setOnClickListener()
        {
            val duration = Toast.LENGTH_SHORT
            Toast.makeText(applicationContext, "ВСЁ ОК", duration).show()
            getCurrentData()
        }
    }

    private fun getCurrentData()
    {
        GlobalScope.launch(Dispatchers.IO)
        {
            val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequests::class.java)
            
            val response = api.getSpaceNews().awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                    Log.d(TAG,data.title)

                withContext(Dispatchers.Main)
                {
                    Title.text = data.title
                }
            }
        }
    }
}