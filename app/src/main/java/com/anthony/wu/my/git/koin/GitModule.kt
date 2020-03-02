package com.anthony.wu.my.git.koin

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import com.anthony.wu.my.git.BuildConfig
import com.anthony.wu.my.git.R
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

val gitModule = module {
    single { createOkHttpClient(androidContext()) }
}

object Properties {

    var SERVER_URL = BuildConfig.BASE_URL

    val UTF8 = Charset.forName("UTF-8")

}

fun createOkHttpClient(context: Context, openInterceptor: Boolean = true): OkHttpClient {
    val httpClient = OkHttpClient.Builder()

    httpClient.connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .followRedirects(false)
            .followSslRedirects(false)

    if (openInterceptor) {
        httpClient.addInterceptor(RedirectInterceptor(context))
    }

    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpClient.addInterceptor(
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }

    )

    return httpClient.build()
}


inline fun <reified T> createService(baseUrl: String = BuildConfig.BASE_URL, okHttpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}

private fun createResponse(chain: Interceptor.Chain, request: Request): Response {
    return chain.proceed(
            request.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(request.method, request.body).build()
    )
}



class RedirectInterceptor(val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        try {

            val request = chain.request()
            var response = createResponse(chain, request)

            when (response.code) {

                HTTP_MOVED_PERM -> {
                    //獲取重定向的地址
                    var location = response.headers.get("Location")
                    if (!location.isNullOrEmpty()) {

                        val uri = location.toUri()
                        location = "${uri.scheme}://${uri.host}/"

                        var requestUrl = request.url.toString()
                        requestUrl = requestUrl.replace(BuildConfig.BASE_URL.toRegex(), location)
                        Properties.SERVER_URL = location
                        val newRequest = request.newBuilder().url(requestUrl).build()
                        response = createResponse(chain, newRequest)
                    } else {
                        throw IOException(response.toString())
                    }

                }
                HTTP_OK -> {

                }
                HTTP_UNAUTHORIZED -> {

                }
                HTTP_UNAVAILABLE -> {


                }
//                in 400..900 -> {
//                    throw IOException(response.toString())
//
//                }
            }

            return response

        } catch (e: Exception) {

            when (e) {
                is UnknownHostException, is SocketTimeoutException -> {

                    Log.e("API Exception", "Exception $e")

                }

            }

            when {
                e is ServiceException -> throw IOException(e.message)
                else -> {
                    Log.e("API Exception", "IOException $e")
                    throw IOException(context.getString(R.string.redirection_failed))
                }
            }

        }
    }

    private fun getResponseBody(responseBody: ResponseBody?): String? {

        var body: String? = null

        responseBody?.let {

            val contentLength = responseBody.contentLength()

            if (contentLength != 0L) {

                val source = responseBody.source()
                source.request(Integer.MAX_VALUE.toLong()) // Buffer the entire body.
                val buffer = source.buffer

                var charset: Charset? = Properties.UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(Properties.UTF8)
                }

                body = buffer.clone().readString(charset!!)
            }
        }

        return body

    }


    private class ServiceException(message: String) : Exception(message)

}


