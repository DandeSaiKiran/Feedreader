package com.peppersquare.dande.feedreader

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by dande on 19-08-2016.
 */
class FeedClient {
    private val BASE_URL: String = "http://test.peppersquare.com/"
    fun getClient(): Retrofit {
       val okHttp = OkHttpClient.Builder()
        .addInterceptor (LoggingInterceptor())
        .build()

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .build()
    }

}