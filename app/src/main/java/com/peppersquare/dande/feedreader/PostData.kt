package com.peppersquare.dande.feedreader

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_post_data.*

class PostData : AppCompatActivity() {

    private val TAG = "Post Data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_data)

        postButton.setOnClickListener {

            val returnIntent = Intent()
            returnIntent.putExtra("title", titleEdit.text.toString())
            returnIntent.putExtra("author", author.text.toString())
            returnIntent.putExtra("description", description.text.toString())
            returnIntent.putExtra("image", R.drawable.facebook)

            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

    }


}
