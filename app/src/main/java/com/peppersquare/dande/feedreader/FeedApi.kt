package com.peppersquare.dande.feedreader


import android.support.v7.widget.DialogTitle
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by dande on 19-08-2016.
 */
interface FeedApi {


    @GET("api/v1/article")
    fun getApiDetails(@Query("author") author: String): Call<List<FeedReaderModel>>


    @POST("api/v1/article")
    fun postApiDetails(@Body  feedReaderModel: FeedReaderModel): Call<FeedReaderModel>

}