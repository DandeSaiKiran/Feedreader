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
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_post_data.*
import kotlinx.android.synthetic.main.feedlist.*
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

                    Log.e(TAG,"get data : " +response)
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

            Log.e(TAG, "GET " + resultCode)
            val title = resultIntent?.getStringExtra("title")
            val author = resultIntent?.getStringExtra("author")
            val description = resultIntent?.getStringExtra("description")
         //   val image = resultIntent?.getStringExtra("image")



            val feedApi1: FeedApi = FeedClient().getClient().create(FeedApi::class.java)

            val call1: Call<FeedReaderModel> = feedApi1.postApiDetails(
            feedReaderModel = FeedReaderModel(title = title!!,
                    author = author!!,
                    description = description!!,
                    image = "https://d262ilb51hltx0.cloudfront.net/max/400/1*sxxTVuaXGa0AUdAyYhwwSw.jpeg"))

            call1?.enqueue(object : Callback<FeedReaderModel>{
                override fun onResponse(call: Call<FeedReaderModel>?, response: Response<FeedReaderModel>?) {
                    Log.e(TAG,"POST OnResponce 1 : "+resultCode)
                    if (response!!.isSuccessful){

                        Log.e(TAG,"POST is sucess : "+resultCode)
                        arrayList.add(response!!.body())
                        feedAdapter.notifyItemInserted(arrayList.size)
                        Log.e(TAG,"POST data "+resultCode)
                    }
                }

                override fun onFailure(call: Call<FeedReaderModel>?, t: Throwable?) {

                    Log.e(TAG,"On Failure :" +resultCode)
                }


            })



/*
                          val feedModel = FeedReaderModel(title = title!!,
                        author = author!!,
                        description = description!!,
                        image = image!!)

                arrayList.add(feedModel)
                feedAdapter.notifyItemInserted(arrayList.size-1)
                println("Feed Model  $feedModel")

*/

            }
        }
    }
}
