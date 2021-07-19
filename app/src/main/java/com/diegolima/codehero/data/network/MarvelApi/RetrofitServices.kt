package com.diegolima.codehero.data.network.MarvelApi

import com.diegolima.codehero.constants.*
import com.diegolima.codehero.data.network.MarvelApi.model.Responses
import com.diegolima.codehero.data.network.loadthumbnail.md5
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface RetrofitServices {
    @GET(CHARACTERS)
    fun allCharacters(
        @Query(OFFSET) offset: Int? = 0,
        @Query(LIMIT) limit: Int? = 4,
    ): Observable<Responses>

    companion object {
        fun getService(): RetrofitServices {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHhttpClient = OkHttpClient.Builder()
            okHhttpClient.addInterceptor(httpLoggingInterceptor)
            okHhttpClient.addInterceptor {
                val request = it.request()
                val httpUrl = request.url()

                val ts =
                    (Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE_UTC)).timeInMillis / 1000L).toString()
                val url = httpUrl.newBuilder()
                    .addQueryParameter(APIKEY, MARVEL_PUBLIC_API_KEY)
                    .addQueryParameter(TS, ts)
                    .addQueryParameter(HASH, "$ts$MARVEL_PRIVATE_KEY$MARVEL_PUBLIC_API_KEY".md5())
                    .build()

                it.proceed(request.newBuilder().url(url).build())
            }

            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(MARVEL_URL_BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHhttpClient.build())
                .build()

            return retrofit.create(RetrofitServices::class.java)
        }
    }
}