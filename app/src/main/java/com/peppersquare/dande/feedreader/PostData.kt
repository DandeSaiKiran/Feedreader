package com.peppersquare.dande.feedreader

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_post_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Serializable
import java.util.*

class PostData : AppCompatActivity() {

    private val TAG = "Post Data"
    val arrayList = ArrayList<FeedReaderModel>()
    val feedAdapter = FeedAdapter(arrayList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_data)

        postButton.setOnClickListener {

            val returnIntent = Intent()
            returnIntent.putExtra("title", titleEdit.text.toString())
            returnIntent.putExtra("author", author.text.toString())
            returnIntent.putExtra("description", description.text.toString())
            returnIntent.putExtra("image", R.drawable.facebook)

          /*  val feedApi: FeedApi = FeedClient().getClient().create(FeedApi::class.java)
            val call: Call<FeedReaderModel> = feedApi.postApiDetails(
                    feedReaderModel = FeedReaderModel(title = titleEdit.text.toString(),
                            author = author.text.toString(),
                            description = description.text.toString(),
                            image = "https://d262ilb51hltx0.cloudfront.net/max/400/1*sxxTVuaXGa0AUdAyYhwwSw.jpeg"))


            call.enqueue(object : Callback<FeedReaderModel>{

                override fun onResponse(call: Call<FeedReaderModel>?, response: Response<FeedReaderModel>) {
                    val statusCode: Int? = response!!.code()
                    arrayList.add(response!!.body())
                    feedAdapter.notifyItemInserted(arrayList.size-1)
                    Log.e(TAG,"PoST   "+statusCode)
                }
                override fun onFailure(call: Call<FeedReaderModel>?, t: Throwable?) {

                }
            })*/
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}
