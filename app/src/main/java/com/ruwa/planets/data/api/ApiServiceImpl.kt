package com.ruwa.planets.data.api

import com.ruwa.planets.data.model.PlanetResults
import io.reactivex.Observable

class ApiServiceImpl : ApiService {
    override fun getPlanets(page: Int?): Observable<PlanetResults> {
        return ServiceBuilder.buildService().getPlanets(page)
    }
}