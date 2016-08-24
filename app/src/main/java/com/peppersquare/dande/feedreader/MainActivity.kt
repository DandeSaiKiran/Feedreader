package com.peppersquare.dande.feedreader

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
            override fun onResponse(call: Call<List<FeedReaderModel>>?, response: Response<List<FeedReaderModel>>) {
                if (response.isSuccessful) {
                    arrayList.addAll(response.body().sortedByDescending { it.id  })
                    feedAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<FeedReaderModel>>?, t: Throwable?) {
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

                val feedApi: FeedApi = FeedClient().getClient().create(FeedApi::class.java)

                val feedModel = FeedReaderModel(title = title!!,
                        author = author!!,
                        description = description!!,
                        image = "https://d262ilb51hltx0.cloudfront.net/max/400/1*sxxTVuaXGa0AUdAyYhwwSw.jpeg")

                val call1: Call<FeedReaderModel> = feedApi.postApiDetails(feedReaderModel = feedModel)

                call1.enqueue(object : Callback<FeedReaderModel> {
                    override fun onResponse(call: Call<FeedReaderModel>?, response: Response<FeedReaderModel>) {
                        if (response.isSuccessful) {

                            feedModel.id = response.body().id
                            arrayList.add(0,feedModel)
                            feedAdapter.notifyItemInserted(0)
                            recyclerView.scrollToPosition(0)
                        }
                    }

                    override fun onFailure(call: Call<FeedReaderModel>?, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            }
        }
    }
}
