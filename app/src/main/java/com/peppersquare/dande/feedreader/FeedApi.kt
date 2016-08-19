package com.peppersquare.dande.feedreader

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by dande on 19-08-2016.
 */
interface FeedApi {

    @GET("api/v1/article")
    fun getApiDetails(@Query("author") author: String): Call<List<FeedReaderModel>>

}