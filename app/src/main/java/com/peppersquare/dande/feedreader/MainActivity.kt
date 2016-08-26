package com.peppersquare.dande.feedreader

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Base64
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.feedlist.*
import kotlinx.android.synthetic.main.feedlist.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MyActivity"
    val POST_REQUEST_CODE = 100
    val arrayList = ArrayList<FeedReaderModel>()
    val feedAdapter = FeedAdapter(arrayList)

    var progressDialog : ProgressDialog? = null
    var isLoading: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = feedAdapter

        val feedApi: FeedApi = FeedClient().getClient().create(FeedApi::class.java)
        val call: Call<List<FeedReaderModel>> = feedApi.getApiDetails("")
        showDialog()

        call.enqueue(object : Callback<List<FeedReaderModel>> {
            override fun onResponse(call: Call<List<FeedReaderModel>>?, response: Response<List<FeedReaderModel>>) {
                if (response.isSuccessful) {
                    hideDialog()
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

    fun showDialog(){
        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("Loading please wait..")
        progressDialog!!.show()
    }

    private fun hideDialog() {
        isLoading = false
        if (progressDialog != null) {
            progressDialog!!.cancel()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, resultIntent: Intent?) {
        Log.d(TAG,"onActivityResult  called with requestCode $requestCode and resultCode $resultCode")
        if (requestCode == POST_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                val title = resultIntent?.getStringExtra("title")
                val author = resultIntent?.getStringExtra("author")
                val description = resultIntent?.getStringExtra("description")
                val image = resultIntent?.getStringExtra("image")
                val feedApi: FeedApi = FeedClient().getClient().create(FeedApi::class.java)

                Log.d(TAG,"onActivityResult  resultIntent: title =  $title image = $image")

                val feedModel = FeedReaderModel(title = title!!,
                        author = author!!,
                        description = description!!,
                        image = image!!)
                Log.d(TAG,"likes: $title")
                val call1: Call<FeedReaderModel> = feedApi.postApiDetails(feedReaderModel = feedModel)

                showDialog()
                call1.enqueue(object : Callback<FeedReaderModel> {
                    override fun onResponse(call: Call<FeedReaderModel>?, response: Response<FeedReaderModel>) {
                        if (response.isSuccessful) {

                            hideDialog()
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
