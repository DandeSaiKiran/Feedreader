package com.peppersquare.dande.feedreader

import android.app.Activity
import android.content.ClipDescription
import android.content.Intent
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_post_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MyActivity"
    val POST_REQUEST_CODE = 100
    val arrayList = ArrayList<FeedReaderModel>()
    val feedAdapter = FeedAdapter(arrayList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = feedAdapter


        val feedApi: FeedApi = FeedClient().getClient().create(FeedApi::class.java)
        val call: Call<List<FeedReaderModel>> = feedApi.getApiDetails("")
        call.enqueue(object : Callback<List<FeedReaderModel>> {
            override fun onFailure(call: Call<List<FeedReaderModel>>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<List<FeedReaderModel>>?, response: Response<List<FeedReaderModel>>) {
                if (response.isSuccessful) {
                    arrayList.addAll(response.body())
                    feedAdapter.notifyDataSetChanged()
                }
            }

        })





        fab.setOnClickListener { view ->
            val intent = Intent(view?.context, PostData::class.java)
            startActivityForResult(intent, POST_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultIntent: Intent?) {
        if (requestCode == POST_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                val title = resultIntent?.getStringExtra("title")
                val author = resultIntent?.getStringExtra("author")
                val description = resultIntent?.getStringExtra("description")


                val feedModel = FeedReaderModel(title = title!!,
                        author = author!!,
                        description = description!!)

                println("Feed Model  $feedModel")

                arrayList.add(feedModel)
                feedAdapter.notifyItemInserted(arrayList.size - 1)
            }
        }
    }
}
