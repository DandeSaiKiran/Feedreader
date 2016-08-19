package com.peppersquare.dande.feedreader

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by dande on 19-08-2016.
 */
class FeedClient {

    private val BASE_URL: String = "http://test.peppersquare.com/"
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit{
        if (retrofit  == null){
            retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!
    }
}