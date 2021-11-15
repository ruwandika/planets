package com.ruwa.planets.data.api

import com.ruwa.planets.data.model.PlanetResults
import io.reactivex.Observable

interface ApiService {
    fun getPlanets(page: Int?) : Observable<PlanetResults>
}