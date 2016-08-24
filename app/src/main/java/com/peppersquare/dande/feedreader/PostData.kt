package com.peppersquare.dande.feedreader



import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_post_data.*


class PostData : AppCompatActivity() {

    private val TAG = "Post Data"
    var imageLink : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_data)

        imageView1.setOnClickListener {
            imageLink = "https://pbs.twimg.com/profile_images/651260268236812289/vbpawQ35_normal.png"
        }
        imageView2.setOnClickListener {

            imageLink = "http://icons.iconarchive.com/icons/iconshock/real-vista-mobile/48/android-platform-icon.png"
        }
        imageView3.setOnClickListener {

            imageLink = "https://files.dev47apps.net/img/app_icon.png"
        }


        postButton.setOnClickListener {

            val returnIntent = Intent()
            returnIntent.putExtra("title", titleEdit.text.toString())
            returnIntent.putExtra("author", author.text.toString())
            returnIntent.putExtra("description", description.text.toString())
            returnIntent.putExtra("image", imageLink)

            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}
