package com.ruwa.planets.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getPlanets(page: Int?) = apiService.getPlanets(page)
}