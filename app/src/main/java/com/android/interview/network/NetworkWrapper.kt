package com.android.interview.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.android.interview.BuildConfig
import com.android.interview.BuildConfig.ACCESS_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkWrapper {

    private const val AUTH_HEADER = "Authorization"
    private const val CONTENT_TYPE = "Content-Type"
    private const val APPLICATION_JSON = "application/json"
    private const val ACCEPT_VERSION = "Accept-Version"

    lateinit var retrofit: Retrofit

    private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

    fun init() {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val headerInterceptor = Interceptor {
            val requestBuilder = it.request().newBuilder()

            requestBuilder.header(AUTH_HEADER, "Client-ID $ACCESS_KEY")
            requestBuilder.header(CONTENT_TYPE, APPLICATION_JSON)
            requestBuilder.header(ACCEPT_VERSION, "v1")

            it.proceed(requestBuilder.build())
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(headerInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)

        this.retrofit = builder.build()
    }
}
