package com.mohamedomer.harajtask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mohamedomer.harajtask.R

class info : AppCompatActivity() {
    var title : TextView?= null
    var location : TextView?= null
    var date : TextView?= null
    var username : TextView?= null
    var body : TextView?= null
    var InfoCarImage : ImageView?= null
    var share : Button?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)


        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = findViewById<View>(R.id.InfoCarName) as TextView
        location = findViewById<View>(R.id.InfoDate) as TextView
        date = findViewById<View>(R.id.infiCarLocation) as TextView
        username = findViewById<View>(R.id.infoCarHolderName) as TextView
        body = findViewById<View>(R.id.body) as TextView
        share = findViewById<View>(R.id.share) as Button
        InfoCarImage =findViewById<View>(R.id.InfoCarImage) as ImageView
        try {
            Glide.with(this).load(intent.getStringExtra("thumbURL").toString())
                .error(R.drawable.ic_user).into(InfoCarImage!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        body!!.text =intent.getStringExtra("body").toString()
        title!!.text =  intent.getStringExtra("title").toString()
        date!!.text =  intent.getStringExtra("date").toString()
        username!!.text =  intent.getStringExtra("username").toString()
        location!!.text =  intent.getStringExtra("city").toString()
share!!.setOnClickListener {
    var myIntent =  Intent(Intent.ACTION_SEND);
    myIntent.setType("text/plain");
    var body = title!!.text.toString()
    var sub = "shared";
    myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
    myIntent.putExtra(Intent.EXTRA_TEXT,body);
    startActivity(Intent.createChooser(myIntent, "Share Using"))
}

    }

}