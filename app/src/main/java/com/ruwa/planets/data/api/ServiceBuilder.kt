package com.ruwa.planets.data.api

import com.ruwa.planets.utils.Urls.BASE_PLANET_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Creates retrofit instance to be used for network operations.
 * Single instance will be created.
 */
object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_PLANET_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(PlanetEndPoints::class.java)

    fun buildService(): PlanetEndPoints {
        return retrofit
    }
}