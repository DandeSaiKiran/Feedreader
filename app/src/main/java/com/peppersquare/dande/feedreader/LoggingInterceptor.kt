package com.peppersquare.dande.feedreader


import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.io.IOException

/**
 * Created by gowthaman on 8/11/15.
 */
class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        var requestLog = "Sending request %s on %s%n%s".format(request.url(), chain.connection(), request.headers())
        if (request.method().compareTo("post", ignoreCase = true) == 0) {
            requestLog = "\n" + requestLog + "\n" + bodyToString(request)
        }

        Log.d("API",requestLog)

        val response = chain.proceed(request)
        val t2 = System.nanoTime()

        val responseLog = "Received response for ${response.request().url()} in ${(t2 - t1) / 1e6} Headers[${response.headers()}]"

        val bodyString = response.body().string()

        Log.d("API",bodyString)

        return response.newBuilder().body(ResponseBody.create(response.body().contentType(), bodyString)).build()
        //return response;
    }

    companion object {
        fun bodyToString(request: Request): String {
            try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                copy.body().writeTo(buffer)
                return buffer.readUtf8()
            } catch (e: IOException) {
                return "did not work"
            }

        }
    }
}
