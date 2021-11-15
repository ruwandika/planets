package com.ruwa.planets.data.api

import com.ruwa.planets.data.model.PlanetResults
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanetEndPoints {

    @GET("planets/")
    fun getPlanets(
        @Query("page") page: Int?
    ): Observable<PlanetResults>
}