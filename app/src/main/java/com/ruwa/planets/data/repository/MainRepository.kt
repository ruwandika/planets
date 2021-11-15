package com.ruwa.planets.data.repository

import com.ruwa.planets.data.api.ApiHelper
import com.ruwa.planets.data.model.PlanetResults
import com.ruwa.planets.utils.Urls
import io.reactivex.Observable

class MainRepository(private val apiHelper: ApiHelper) {

    /**
     * Method to get planets.
     * [page] number is required to fetch the planet details from that page.
     */
    fun getPlanets(page: Int?): Observable<PlanetResults> {
        return apiHelper.getPlanets(page)
    }

    /**
     * Method to get planet image urls.
     * Urls with sample images will be created for all the planets from [planetResults]
     */
    fun getPlanetImages(planetResults: PlanetResults) {
        for (planet in planetResults.results) {
            planet.planetImageUrl = Urls.BASE_IMAGE_URL + "${(100..200).random()}/400"
        }
    }
}