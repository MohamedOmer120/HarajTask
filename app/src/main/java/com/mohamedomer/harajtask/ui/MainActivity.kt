package com.mohamedomer.harajtask.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohamedomer.harajtask.R
import com.mohamedomer.harajtask.Support_Class.CarsModel
import com.mohamedomer.harajtask.Support_Class.ListAdapter
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var listview: ListView? = null
    var listModels: List<CarsModel>? = null
    var adapter: ListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listModels = ArrayList()
        listview = findViewById<View>(R.id.CarsList) as ListView
        val jsonFileString = getJsonDataFromAsset(applicationContext, "data.json")
        if (jsonFileString != null) {
            Log.i("data", jsonFileString)
        }
        listview!!.setOnItemClickListener { parent, view, position, id ->
            val element = adapter!!.getItem(position) // The item that was clicked
            val intent = Intent(this, info::class.java)
            intent.putExtra("title",element!!.title)
            intent.putExtra("date",element.date)
            intent.putExtra("username",element.username)
            intent.putExtra("city",element.city)
            intent.putExtra("body",element.body)
            intent.putExtra("thumbURL",element.thumbURL)
            startActivity(intent)
        }

        val gson = Gson()
        val listPersonType = object : TypeToken<List<CarsModel>>() {}.type

        var persons: List<CarsModel> = gson.fromJson(jsonFileString, listPersonType)
        persons.forEachIndexed { idx, person -> Log.i("data", "> Item $idx:\n$person") }

        val carList : JSONArray =  JSONArray(jsonFileString)
        for (i in 0 until carList.length()) {
            val jsonObject: JSONObject = carList.getJSONObject(i)
            val title = jsonObject["title"].toString()
            val username = jsonObject["username"].toString()
            val thumbURL = jsonObject["thumbURL"].toString()
            val commentCount = jsonObject["commentCount"].toString()
            val city = jsonObject["city"].toString()
            val date = jsonObject["date"].toString()
            val body = jsonObject["body"].toString()
            listModels = listModels?.plus(
                listOf(
                    CarsModel(title,username,commentCount.toInt(),city,date,body,thumbURL)
                )
            )

        }
        adapter = ListAdapter(this, listModels as List<CarsModel>)
        listview!!.adapter = adapter
        adapter!!.setNotifyOnChange(true)
        adapter!!.notifyDataSetChanged()


    }

    private fun getJsonDataFromAsset(applicationContext: Context?, s: String): String? {
        val jsonString: String
        try {
            jsonString = applicationContext?.assets?.open(s)?.bufferedReader().use { it?.readText()!! }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }


    fun setValue(Key: String?, Value: String?) {
        val editor = getSharedPreferences("data", MODE_PRIVATE).edit()
        editor.putString(Key, Value)
        editor.apply()
    }

    fun getValue(key: String?): String? {
        val prefs = getSharedPreferences("data", MODE_PRIVATE)
        return prefs.getString(key, "No data")
    }

}